package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javalin.http.Context;
import java.util.List;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.HandlerAuthenticator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This is a handler for user requests to get a full list of existing
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost ClientHosts}
 * */
public class GetAllClientHostsHandler extends HandlerAuthenticator {

    private static final Logger LOGGER = Logger.getLogger(
        GetAllClientHostsHandler.class
    );

    private final ClientHostService clientHostService;

    private final ModelMapper modelMapper;

    private final Function<Object, String> responseConversionStrategy;

    /**
     * A default constructor for dependancies injection
     *
     * @param authenticationStrategy     a strategy for {@link User} authentication
     * @param clientHostService          a service for retrieving
     *                                   {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost client hosts}
     * @param modelMapper                a mapper for converting db-entities
     *                                   to representation-layer POJOs
     * @param responseConversionStrategy a strategy of converting
     *                                   response entities
     *                                   to a {@link String} representation
     * */
    public GetAllClientHostsHandler(
        AuthenticationStrategy authenticationStrategy,
        ClientHostService clientHostService,
        ModelMapper modelMapper,
        Function<Object, String> responseConversionStrategy
    ) {
        super(authenticationStrategy);
        this.clientHostService = clientHostService;
        this.modelMapper = modelMapper;
        this.responseConversionStrategy = responseConversionStrategy;
    }

    @Override
    public void handleAuthenticated(Context context, User user) {
        context.result(
            responseConversionStrategy.apply(
                modelMapper.map(
                    clientHostService.getAll(),
                    new TypeToken<List<ClHstDTO>>() { }.getType()
                )
            )
        );
    }

    /**
     * This class is just an information container to response
     * Th
     * */
    @JsonIgnoreProperties({"private_key"})
    private static class ClHstDTO extends ClientHostDTO {
    }
}
