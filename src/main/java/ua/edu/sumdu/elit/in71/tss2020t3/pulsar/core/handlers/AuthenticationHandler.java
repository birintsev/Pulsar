package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

// just a dummy for further use of another authentication type (e.g. JWT)
public class AuthenticationHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        AuthenticationHandler.class
    );

    private final UserService userService;

    /**
     * A default constructor
     *
     * @param userService a service for retrieving a {@link User} instance
     * */
    public AuthenticationHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(@NotNull Context ctx) {
        String userEmail;
        String userPassword;
        User user;
        if (!ctx.basicAuthCredentialsExist()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Basic credentials not found"
            );
        }
        userEmail = ctx.basicAuthCredentials().getUsername();
        userPassword = ctx.basicAuthCredentials().getPassword();
        user = userService.findByEmail(userEmail);
        if (
            user == null
            || !user.getPassword().equals(userPassword)
            || !userService.isActive(user.getId())
        ) {
            LOGGER.error(
                "The user does not exist"
                + " (or is not active) or wrong password specified: "
                + ctx.basicAuthCredentials().getUsername()
            );
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Bad credentials"
            );
        } else {
            LOGGER.trace("User (email = " + userEmail + ") authenticated");
            ctx.status(HttpStatus.Code.OK.getCode());
        }
    }
}
