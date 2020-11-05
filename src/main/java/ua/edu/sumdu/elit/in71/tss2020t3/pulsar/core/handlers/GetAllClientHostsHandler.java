package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * This is a {@link Handler} for requests
 * to receive all the {@link ClientHost}s owned by a {@link User}
 * <p>
 * It returns all the {@link ClientHost} owned by a user
 * who reaches the endpoint.
 * */
public class GetAllClientHostsHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        GetAllClientHostsHandler.class
    );

    private final UserService userService;

    private final ClientHostService clientHostService;

    private final ModelMapper modelMapper;

    /**
     * A default constructor
     *
     * @param userService       a service for retrieving a {@link User} object
     * @param clientHostService a service for retrieving
     *                          {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic}
     *                          objects
     * @param modelMapper       a {@link ModelMapper} for converting
     *                          {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic} objects to {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO}
     *                          objects
     * */
    public GetAllClientHostsHandler(
        UserService userService,
        ClientHostService clientHostService,
        ModelMapper modelMapper
    ) {
        this.userService = userService;
        this.clientHostService = clientHostService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        ctx.result(convertToResponse(clientHostService.getByOwner(user)));
    }

    private String convertToResponse(
        Set<ClientHost> clientHosts
    ) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
            clientHosts
                .stream()
                .map(ch -> modelMapper.map(ch, ClientHostDTO.class))
                .collect(Collectors.toSet())
        );
    }
}
