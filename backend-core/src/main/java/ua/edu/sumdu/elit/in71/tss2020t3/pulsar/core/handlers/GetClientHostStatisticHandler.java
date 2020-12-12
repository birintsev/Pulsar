package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserAccessException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * A handler for handling the request to get a
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic}
 * */
public class GetClientHostStatisticHandler
extends UserRequestHandler<ClientHostStatisticRequest> {

    private static final Logger LOGGER = Logger.getLogger(
        GetClientHostStatisticHandler.class
    );

    private final ClientHostStatisticService clientHostStatisticService;

    private final ModelMapper modelMapper;

    private final Function<Object, String> responseWritingStrategy;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy        a strategy for user authentication
     * @param validator                     a validator for user requests
     * @param requestConverter              a user request converter
     * @param clientHostStatisticService    a service for working with portions
     *                                      of a client host statistic
     * @param modelMapper                   a mapper for converting
     *                                      business-logic level entities
     *                                      to their representation-layer
     *                                      analogues
     * @param responseWritingStrategy       a default object toString-converting
     *                                      strategy
     * */
    public GetClientHostStatisticHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, ClientHostStatisticRequest> requestConverter,
        ClientHostStatisticService clientHostStatisticService,
        ModelMapper modelMapper,
        Function<Object, String> responseWritingStrategy
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.clientHostStatisticService = clientHostStatisticService;
        this.modelMapper = modelMapper;
        this.responseWritingStrategy = responseWritingStrategy;
    }

    @Override
    public void handleUserRequest(
        ClientHostStatisticRequest request,
        User requester,
        Context context
    ) {
        try {
            context.result(
                responseWritingStrategy.apply(
                    modelMapper.map(
                        clientHostStatisticService.getByPublicKey(
                            requester,
                            request.getPublicKey(),
                            request.getStartDate(),
                            request.getEndDate()
                        ),
                        new TypeToken<List<ClientHostStatisticDTO>>() {
                        }.getType()
                    )
                )
            );
        } catch (UserAccessException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "The user is not authorized to view the "
                    + (e.getAccessedObject() instanceof ClientHost
                        ? ((ClientHost) e.getAccessedObject()).getName() + " "
                        : ""
                    )
                    + "host statistic"
            );
        }
    }

    @AllArgsConstructor
    public static class RequestReadingStrategy
    implements Function<Context, ClientHostStatisticRequest> {

        public static final String PUBLIC_KEY_QUERY_PARAM_NAME = "public_key";

        public static final String START_DATE_QUERY_PARAM_NAME = "start_date";

        public static final String END_DATE_QUERY_PARAM_NAME = "end_date";

        private final Function<String, ZonedDateTime>
        zonedDateTimeReadingStrategy;

        @Override
        public ClientHostStatisticRequest apply(Context context) {
            return new ClientHostStatisticRequest(
                context.queryParam(PUBLIC_KEY_QUERY_PARAM_NAME),
                zonedDateTimeReadingStrategy.apply(
                    context.queryParam(START_DATE_QUERY_PARAM_NAME)
                ),
                zonedDateTimeReadingStrategy.apply(
                    context.queryParam(END_DATE_QUERY_PARAM_NAME)
                )
            );
        }
    }
}
