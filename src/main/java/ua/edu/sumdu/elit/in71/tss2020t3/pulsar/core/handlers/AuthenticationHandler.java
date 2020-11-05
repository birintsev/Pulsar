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
        String userEmail = ctx.basicAuthCredentials().getUsername();
        String userPassword = ctx.basicAuthCredentials().getPassword();
        User user = userService.findByEmail(userEmail);
        if (user == null || !user.getPassword().equals(userPassword)) {
            LOGGER.error("The user does not exist or wrong password specified: "
                + ctx.basicAuthCredentials().getUsername()
            );
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad credentials"
            );
        } else {
            LOGGER.trace("User (email = " + userEmail + ") authenticated");
            ctx.status(HttpStatus.Code.OK.getCode());
        }
    }
}
