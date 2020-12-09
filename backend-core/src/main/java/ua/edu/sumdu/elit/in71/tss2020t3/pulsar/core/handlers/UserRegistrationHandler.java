package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRegistrationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * This class represents a controller for new user creation requests
 * (registration requests)
 * */
@AllArgsConstructor
public class UserRegistrationHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UserRegistrationHandler.class
    );

    private final Validator validator;

    private final Function<UserRegistrationDTO, User> dtoConverter;

    private final Function<String, UserRegistrationDTO> deserializer;

    private final UserService userService;

    private final MailService mailService;

    @Override
    public void handle(@NotNull Context ctx) {
        UserRegistrationDTO dto;
        User user;
        try {
            dto = deserializer.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(
                "Error during deserialization "
                    + "request body (below): "
                    + System.lineSeparator()
                    + ctx.body()
                    + System.lineSeparator()
                    + "to UserRegistrationDTO"
                , e
            );
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request format"
            );
        }
        validate(dto);
        try {
            user = userService.registerUser(
                dtoConverter.apply(dto)
            );
            mailService.sendRegistrationConfirmationEmail(
                userService.getRegistrationConfirmationFor(user)
            );
        } catch (AlreadyExistsException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "A user with such mail or username already registered"
            );
        }
        ctx.status(HttpStatus.Code.OK.getCode());
        LOGGER.trace(
            "User (email=" + user.getId().getEmail()
                + ") has been registered registered"
        );
    }

    private void validate(UserRegistrationDTO dto) {
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations
            = validator.validate(dto);
        if (!constraintViolations.isEmpty()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request",
                constraintViolations.toArray(new ConstraintViolation[0])
            );
        }
    }
}
