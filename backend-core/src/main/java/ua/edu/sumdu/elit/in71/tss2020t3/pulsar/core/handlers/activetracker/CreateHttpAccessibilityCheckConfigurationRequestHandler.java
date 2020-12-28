package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.activetracker;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration.MIN_CHECK_INTERVAL;

import io.javalin.http.Context;
import java.time.ZonedDateTime;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker.CreateHttpAccessibilityCheckConfigurationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This is a handler for
 * */
public class CreateHttpAccessibilityCheckConfigurationRequestHandler extends
    UserRequestHandler<CreateHttpAccessibilityCheckConfigurationRequest> {

    private static final Logger LOGGER = Logger.getLogger(
        CreateHttpAccessibilityCheckConfigurationRequestHandler.class
    );

    private final HttpAccessibilityService accessibilityService;

    /**
     * A default constructor for dependency injection
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator for user requests
     * @param accessibilityService   a business logic provider
     *                               for active checks management
     * @param requestConverter       a converter for retrieving POJO objects
     */
    public CreateHttpAccessibilityCheckConfigurationRequestHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        HttpAccessibilityService accessibilityService,
        Function<Context, CreateHttpAccessibilityCheckConfigurationRequest>
            requestConverter
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.accessibilityService = accessibilityService;
    }

    @Override
    public void handleUserRequest(
        CreateHttpAccessibilityCheckConfigurationRequest request,
        User requester,
        Context context
    ) {
        try {
            accessibilityService.subscribe(
                requester,
                accessibilityService.createIfNotExist(
                    request.getTargetUrl(),
                    request.getResponseTimeout(),
                    request.getInterval()
                ),
                ZonedDateTime.now()
            );
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_GATEWAY.getCode(),
                "Minimal allowed check interval is " + MIN_CHECK_INTERVAL
            );
        }
    }
}
