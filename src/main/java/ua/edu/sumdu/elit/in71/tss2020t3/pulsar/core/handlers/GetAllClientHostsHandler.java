package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.OrganisationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.UserClientHostsResponse;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
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

    private final OrganisationService organisationService;

    private final ModelMapper modelMapper;

    /**
     * A default constructor
     *
     * @param userService         a service for retrieving a {@link User} object
     * @param clientHostService   a service for retrieving
     *                            {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic}
     *                            objects
     * @param modelMapper         a {@link ModelMapper} for converting
     *                            {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic} objects to {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO}
     *                            objects
     * @param organisationService a service for retrieving {@link Organisation}
     *                            objects
     * */
    public GetAllClientHostsHandler(
        UserService userService,
        ClientHostService clientHostService,
        ModelMapper modelMapper,
        OrganisationService organisationService
    ) {
        this.userService = userService;
        this.clientHostService = clientHostService;
        this.modelMapper = modelMapper;
        this.organisationService = organisationService;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        Set<ClientHost> ownedClientHosts =
            clientHostService.getByOwner(user);
        Set<ClientHost> subscribedClientHosts =
            clientHostService.getBySubscriber(user);
        Map<Organisation, Set<ClientHost>> organisationClientHosts =
            new HashMap<>();
        Set<Organisation> userOrganisations =
            organisationService.getByMember(user);
        userOrganisations.forEach(
            organisation -> organisationClientHosts.put(
                organisation,
                organisationService.getOrganisationClientHosts(organisation)
            )
        );
        ctx.result(
            convertToResponse(
                ownedClientHosts,
                subscribedClientHosts,
                organisationClientHosts
            )
        );
    }

    private String convertToResponse(
        Set<ClientHost> ownedClientHosts,
        Set<ClientHost> subscribedClientHosts,
        Map<Organisation, Set<ClientHost>> organisationClientHosts
    ) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
            new UserClientHostsResponse(
                modelMapper.map(
                    ownedClientHosts,
                    new TypeToken<List<ClientHostDTO>>() { }.getType()
                ),
                modelMapper.map(
                    subscribedClientHosts,
                    new TypeToken<List<ClientHostDTO>>() { }.getType()
                ),
                organisationClientHosts
                    .entrySet()
                    .stream()
                    .map(
                        organisationSetEntry ->
                            new UserClientHostsResponse.OrganisationClientHosts(
                                modelMapper.map(
                                    organisationSetEntry.getKey(),
                                    OrganisationDTO.class
                                ),
                                modelMapper.map(
                                    organisationSetEntry.getValue(),
                                    new TypeToken<List<ClientHostDTO>>() {
                                    }.getType()
                                )
                        )
                    )
                    .collect(Collectors.toList())
            )
        );
    }
}
