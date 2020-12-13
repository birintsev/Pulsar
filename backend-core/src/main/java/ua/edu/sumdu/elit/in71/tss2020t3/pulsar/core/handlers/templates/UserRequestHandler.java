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

    protected static final String USER_CONTEXT_ATTRIBUTE_NAME =
        User.class.getName();

    protected static final String REQUEST_CONTEXT_ATTRIBUTE_NAME =
        "REQUEST";

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

        authenticator = new HandlerAuthenticator(
            authenticationStrategy
        ) {
            @Override
            public void handleAuthenticated(
                Context ctx,
                User user
            ) {
                ctx.attribute(USER_CONTEXT_ATTRIBUTE_NAME, user);
            }
        };
        handlerValidator = new HandlerValidator<REQUEST>(
            validator,
            requestConverter
        ) {
            @Override
            public void handleValid(
                Context ctx,
                REQUEST rqst
            ) {
                ctx.attribute(REQUEST_CONTEXT_ATTRIBUTE_NAME, rqst);
            }
        };
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        LOGGER.trace("beforeProcess: " + ctx.endpointHandlerPath());
        beforeProcess(ctx); /*[Step #1]*/
        LOGGER.trace("process: " + ctx.endpointHandlerPath());
        process(ctx);       /*[Step #2]*/
        LOGGER.trace("afterProcess: " + ctx.endpointHandlerPath());
        afterProcess(ctx);  /*[Step #3]*/
    }

    protected void beforeProcess(Context request) throws Exception {
        authenticator.handle(request);
        handlerValidator.handle(request);
    }

    protected void process(Context context) {
        handleUserRequest(
            context.attribute(REQUEST_CONTEXT_ATTRIBUTE_NAME),
            context.attribute(USER_CONTEXT_ATTRIBUTE_NAME),
            context
        );
    }

    protected void afterProcess(Context request) {
        // may be overridden in future extensions of this class
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
    public abstract void handleUserRequest(
        REQUEST request,
        User requester,
        Context context
    );
}
