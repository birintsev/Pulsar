package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

@AllArgsConstructor
public class UserResetPasswordHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UserResetPasswordHandler.class
    );

    private final UserService userService;

    private final Function<String, UserResetPasswordDTO> dtoConverter;

    private final Validator validator;

    @Override
    public void handle(@NotNull Context ctx) {
        UserResetPasswordDTO dto;
        try {
            dto = dtoConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error("Can not parse a DTO from the request body", e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Invalid request body"
            );
        }
        try {
            userService.resetPasswordByRequest(
                dto.getResetKey(), dto.getPassword()
            );
        } catch (IllegalStateException e) {
            LOGGER.error(
                "Request to reset the user password get,"
                    + " but the user has not requested to reset the password"
            );
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Please, submit a request to reset the password first"
            );
        } catch (IllegalArgumentException e) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Invalid request",
                validator.validate(dto).toArray(new ConstraintViolation[0])
            );
        }
        ctx.status(HttpStatus.Code.OK.getCode());
    }
}
