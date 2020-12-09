package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_PREMIUM_ACCOUNT;

import io.javalin.http.Context;
import java.util.NoSuchElementException;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UpdateUserStatusDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * A handler for updating {@link User} statuses
 * */
public class UpdateUserStatusHandler
extends UserRequestHandler<UpdateUserStatusDTO> {

    private static final Logger LOGGER = Logger.getLogger(
        UpdateUserStatusHandler.class
    );

    private final UserService userService;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator for a request entity
     * @param userService            a service for managing user statuses
     * @param requestConverter       a function for converting
     *                               a {@link Context user request}
     *                               to its {@link UpdateUserStatusDTO POJO}
     *                               representation
     * */
    public UpdateUserStatusHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, UpdateUserStatusDTO> requestConverter,
        UserService userService
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.userService = userService;
    }

    @Override
    public void handleUserRequest(
        UpdateUserStatusDTO request,
        User requester,
        Context context
    ) {
        switch (request.getAction()) {
            case ADD:
                try {
                    userService.addUserStatus(
                        requester,
                        new UserStatus(request.getStatus())
                    );
                } catch (NoSuchElementException e) {
                    LOGGER.error(e);
                    throw new JsonHttpResponseException(
                        HttpStatus.Code.BAD_REQUEST.getCode(),
                        "Status " + request.getStatus() + " does not exist"
                    );
                }
                break;
            case REMOVE:
                try {
                    userService.removeStatus(
                        requester,
                        new UserStatus(request.getStatus())
                    );
                } catch (UserStatusException e) {
                    LOGGER.error(e);
                    throw new JsonHttpResponseException(
                        HttpStatus.Code.FORBIDDEN.getCode(),
                        "The user is a member/owner of an organisation."
                            + " Consider leaving all the organisations "
                            + "before removing "
                            + USER_STATUS_PREMIUM_ACCOUNT
                            + " status"
                    );
                }
                break;
            default:
                throw new JsonHttpResponseException(
                    HttpStatus.Code.BAD_REQUEST.getCode(),
                    "Unsupported action " + request.getAction()
                );
        }
    }
}
