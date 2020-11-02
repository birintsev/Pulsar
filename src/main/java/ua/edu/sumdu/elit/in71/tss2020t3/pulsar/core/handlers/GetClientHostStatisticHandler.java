package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * A handler for handling the request to get a {@link ClientHostStatistic}
 * */
public class GetClientHostStatisticHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        GetClientHostStatisticHandler.class
    );

    private static final String PUBLIC_KEY_QUERY_PARAM_NAME = "publicKey";

    private static final String PAGE_SIZE_QUERY_PARAM_NAME = "pageSize";

    private static final String START_PAGE_QUERY_PARAM_NAME = "startPage";

    private final UserService userService;

    private final ClientHostService clientHostService;

    private final ClientHostStatisticService clientHostStatisticService;

    private final Validator validator;

    private final Function<ClientHostStatistic, ClientHostStatisticDTO>
        responseConverter;

    /**
     * A default constructor for dependency injection
     *
     * @param userService                a service for working with
     *                                   {@link User}s
     * @param clientHostService          a service for working with
     *                                   {@link ClientHost}s
     * @param clientHostStatisticService a service for working with
     *                                   {@link ClientHostStatistic}s
     * @param validator                  a javax validator
     * @param responseConverter          a converter for statistic container
     *                                   objects from their business-level POJOs
     *                                   to representation-level POJOs
     * */
    public GetClientHostStatisticHandler(
        UserService userService,
        ClientHostService clientHostService,
        ClientHostStatisticService clientHostStatisticService,
        Validator validator,
        Function<ClientHostStatistic, ClientHostStatisticDTO> responseConverter
    ) {
        this.userService = userService;
        this.clientHostService = clientHostService;
        this.clientHostStatisticService = clientHostStatisticService;
        this.validator = validator;
        this.responseConverter = responseConverter;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        ClientHostStatisticRequest request = createFromContextRequestURL(ctx);
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        if (!isUserOwnerOfHost(user, request.getPublicKey())) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "The user is not an owner of the host"
            );
        }
        List<ClientHostStatistic> statistic =
            clientHostStatisticService.getByPublicKey(request.getPublicKey());
        ctx.status(HttpStatus.Code.OK.getCode());
        ctx.result(convertToResponse(statistic));
    }

    private String convertToResponse(
        List<ClientHostStatistic> statistics
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<ClientHostStatisticDTO> dtos = new ArrayList<>(statistics.size());
        statistics.stream().map(responseConverter).forEach(dtos::add);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writeValueAsString(dtos);
    }

    private ClientHostStatisticRequest createFromContextRequestURL(
        Context ctx
    ) {
        ClientHostStatisticRequest dto = new ClientHostStatisticRequest(
            ctx.queryParam(PUBLIC_KEY_QUERY_PARAM_NAME),
            ctx.queryParam(PAGE_SIZE_QUERY_PARAM_NAME, int.class, "10").get(),
            ctx.queryParam(START_PAGE_QUERY_PARAM_NAME, int.class, "1").get()
        );
        Set<ConstraintViolation<ClientHostStatisticRequest>> validationResult =
            validator.validate(dto);
        if (!validationResult.isEmpty()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                null,
                validationResult.toArray(new ConstraintViolation[0])
            );
        } else {
            return dto;
        }
    }

    /**
     * Checks if user is authorized to view the statistic of the
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
     * associated with teh {@code publicKey}
     * */
    private boolean isUserOwnerOfHost(User user, String publicKey) {
        ClientHost clientHost = clientHostService.getByPublicKey(publicKey);
        if (clientHost == null) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.NOT_FOUND.getCode(),
                "Client host not found",
                Collections.emptyMap()
            );
        }
        return clientHost.getOwner().equals(user);
    }
}
