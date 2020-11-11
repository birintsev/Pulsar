package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRequestToResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * This handler creates a key (
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest#getResetKey()}
 * ) for a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User}
 * password reset
 * */
@AllArgsConstructor
public class UserRequestToResetPasswordHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UserRequestToResetPasswordHandler.class
    );

    private final UserService userService;

    private final MailService mailService;

    private final Function<String, UserRequestToResetPasswordDTO> dtoConverter;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user;
        UserRequestToResetPasswordDTO dto;
        UserResetPasswordRequest resetPasswordRequest;
        try {
            dto = dtoConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(
                "Error during parsing the request body: " + ctx.body()
            );
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Can not find 'email' field"
            );
        }
        user = userService.findByEmail(dto.getEmail());
        resetPasswordRequest = userService.createResetPasswordRequestFor(user);
        if (user != null) {
            mailService.sendResetPasswordEmail(resetPasswordRequest);
        }
        ctx.status(HttpStatus.Code.OK.getCode());
        ctx.result(
            new ObjectMapper()
                .createObjectNode()
                .put(
                    "message",
                    "A reset-password link has been sent to user email"
                        + " (if user exists)"
                )
                .toString()
        );
    }
}
