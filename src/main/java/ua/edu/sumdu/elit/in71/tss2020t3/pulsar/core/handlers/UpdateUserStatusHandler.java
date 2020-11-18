package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UpdateUserStatusDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * A handler for updating {@link User} statuses
 * */
@AllArgsConstructor
public class UpdateUserStatusHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UpdateUserStatusHandler.class
    );

    private final UserService userService;

    private final Function<String, UpdateUserStatusDTO> bodyConverter;

    private final Validator validator;

    @Override
    public void handle(@NotNull Context ctx) {
        UpdateUserStatusDTO body;
        Set<ConstraintViolation<UpdateUserStatusDTO>> validationResult;
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        try {
            body = bodyConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request"
            );
        }
        validationResult = validator.validate(body);
        if (!validationResult.isEmpty()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request",
                validationResult.toArray(new ConstraintViolation[0])
            );
        }
        handle(body, user);
    }

    private void handle(UpdateUserStatusDTO request, User user) {
        switch (request.getAction()) {
            case ADD:
                try {
                    userService.addUserStatus(
                        user,
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
                userService.removeStatus(
                    user,
                    new UserStatus(request.getStatus())
                );
                break;
            default:
                throw new JsonHttpResponseException(
                    HttpStatus.Code.BAD_REQUEST.getCode(),
                    "Unsupported action " + request.getAction()
                );
        }
    }
}
