package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.AuthenticationException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This class provides a set of common authentication-related actions
 * being preformed on a {@link Context user request}
 * before it handling
 * */
@AllArgsConstructor
public abstract class HandlerAuthenticator implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        HandlerAuthenticator.class
    );

    private final AuthenticationStrategy authenticationStrategy;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user;
        try {
            user = authenticationStrategy.authenticate(ctx);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Bad credentials"
            );
        }
        handleAuthenticated(ctx, user);
    }

    /**
     * This method handles user {@link Context request}
     * after it gets authenticated by {@link #authenticationStrategy}
     *
     * @param context a {@link User user} {@link Context request}
     * @param user a {@link User user} who sent the {@link Context request}
     * */
    public abstract void handleAuthenticated(Context context, User user);
}
