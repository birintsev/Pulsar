package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.activetracker;

import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.validation.Validator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.activetracker.HttpAccessibilityCheckConfigurationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.ToDTOHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This is a handler for retrieving all the host
 * (i.e. a {@link HttpAccessibilityCheckConfiguration set of configurations}
 * for tracking the host) tracked by a user who's sent a request
 * */
public class GetActiveTrackerUserHosts
extends ToDTOHandler<
    Void,
    List<HttpAccessibilityCheckConfiguration>,
    List<HttpAccessibilityCheckConfigurationDTO>
> {

    private final HttpAccessibilityService accessibilityService;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy  a strategy for user authentication
     * @param validator               a validator for user requests
     * @param requestConverter        a converter for retrieving POJO objects
     *                                from user requests
     * @param responseWritingStrategy a strategy for converting a response POJO
     * @param toDtoConverter          a converter that will be used
     *                                for converting a business-leve POJOs to
     *                                their representation-level analogues
     * @param accessibilityService    a service for managing
     *                                {@link HttpAccessibilityCheckConfiguration configuration items}
     * */
    public GetActiveTrackerUserHosts(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, Void> requestConverter,
        Function<List<HttpAccessibilityCheckConfigurationDTO>, String>
            responseWritingStrategy,
        Function<
            List<HttpAccessibilityCheckConfiguration>,
            List<HttpAccessibilityCheckConfigurationDTO>
        > toDtoConverter,
        HttpAccessibilityService accessibilityService
    ) {
        super(
            authenticationStrategy,
            validator,
            requestConverter,
            responseWritingStrategy,
            toDtoConverter
        );
        this.accessibilityService = accessibilityService;
    }

    @Override
    public void handleUserRequest(
        Void unused,
        User requester,
        Context context
    ) {
        convertAndSet(
            new ArrayList<>(
                accessibilityService.getUserSubscriptions(requester)
            ),
            context
        );
    }
}
