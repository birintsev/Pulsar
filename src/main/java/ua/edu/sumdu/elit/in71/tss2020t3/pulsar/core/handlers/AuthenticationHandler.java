package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.function.Function;
import javax.naming.AuthenticationException;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.UserDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

// just a dummy for further use of another authentication type (e.g. JWT)
@AllArgsConstructor
public class AuthenticationHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        AuthenticationHandler.class
    );

    private final ModelMapper modelMapper;

    private final Function<UserDTO, String> responseConverter;

    private final AuthenticationStrategy authenticationStrategy;

    @Override
    public void handle(@NotNull Context ctx) {
        User user;
        if (!ctx.basicAuthCredentialsExist()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Basic credentials not found"
            );
        }
        try {
            user = authenticationStrategy.authenticate(ctx);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Bad credentials"
            );
        } catch (UserStatusException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "The account has not been confirmed yet"
            );
        }
        ctx.result(
            responseConverter.apply(modelMapper.map(user, UserDTO.class))
        );
        ctx.status(
            HttpStatus.Code.OK.getCode()
        );
        LOGGER.trace(
            "User (username = "
                + user.getUsername()
                + ") has been authenticated"
        );
    }
}
