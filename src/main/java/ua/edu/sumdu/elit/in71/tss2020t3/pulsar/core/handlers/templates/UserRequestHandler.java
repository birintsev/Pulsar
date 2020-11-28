package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This handler mixes in behavior of 2 handler templates:
 * <ol>
 *     <li>{@link HandlerAuthenticator}
 *     <li>{@link HandlerValidator}
 * </ol>
 * Firstly it authenticates the user
 * (i.e. passes handling to the {@link HandlerAuthenticator authenticator})
 * and secondly, validates the request user sent
 * (i.e. passes handling to the {@link HandlerValidator validator}).
 * After the request has been authenticated and validated,
 * the handling is passed to the {@link #handleUserRequest} method
 *
 * @param <REQUEST> a POJO-representation of user request
 *                  which this class implementation specializes on
 * */
public abstract class UserRequestHandler<REQUEST> implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UserRequestHandler.class
    );

    private final HandlerAuthenticator authenticator;

    private final HandlerValidator<REQUEST> handlerValidator;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator for user requests
     * @param requestConverter       a converter for retrieving POJO objects
     *                               from user requests
     * */
    public UserRequestHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, REQUEST> requestConverter
    ) {
        final String userContextAttributeName = User.class.getName();
        authenticator = new HandlerAuthenticator(
            authenticationStrategy
        ) {
            @Override
            public void handleAuthenticated(/*[Step #2]*/
                Context ctx,
                User user
            ) {
                ctx.attribute(userContextAttributeName, user);
                handlerValidator.handle(ctx);
            }
        };
        handlerValidator = new HandlerValidator<REQUEST>(
            validator,
            requestConverter
        ) {
            @Override
            public void handleValid(/*[Step #3]*/
                Context ctx,
                REQUEST rqst
            ) {
                handleUserRequest(
                    rqst,
                    ctx.attribute(userContextAttributeName),
                    ctx
                );
            }
        };
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        process(ctx);
    }

    private void process(Context request) throws Exception {
        authenticator.handle(request); /*[Step #1]*/
    }

    /**
     * This method provides a possibility
     * to handle a user request with pre-extracted valid POJO from the request
     *
     * @param request   a POJO representation of the user request
     *                  which this handler specializes on
     * @param requester an authenticated user who sent the request
     * @param context   an original request itself
     * */
    public abstract void handleUserRequest(/*[Step #4]*/
        REQUEST request,
        User requester,
        Context context
    );
}
