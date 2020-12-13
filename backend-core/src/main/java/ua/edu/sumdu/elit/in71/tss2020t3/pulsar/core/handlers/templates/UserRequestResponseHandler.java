package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates;

import io.javalin.http.Context;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This class is an extension of the {@link UserRequestHandler}'s workflow.
 * <p>
 * Actually what it does is:
 * <ol>
 *     <li> Converts a response, has been sent in
 *          a {@link UserRequestResponseHandler} implementation
 *          (see {@link #setResult}, {@link #getResult})
 *          using {@link #responseWritingStrategy}
 *     </li>
 *     <li>
 *          Sets a string obtained on the previous step
 *          as a {@link Context request} response
 *     </li>
 * </ol>
 *
 * @param <REQUEST>  a POJO representation of incoming request
 * @param <RESPONSE> an object to be set a response entity
 *                   (e.g. an entity (or a collection of entities)
 *                   requested in a {@link REQUEST})
 * */
public abstract class UserRequestResponseHandler<REQUEST, RESPONSE>
extends UserRequestHandler<REQUEST> {

    protected static final String RESPONSE_CONTEXT_ATTRIBUTE_NAME =
        "HANDLING_RESULT";

    private static final Logger LOGGER = Logger.getLogger(
        UserRequestResponseHandler.class
    );

    private final Function<RESPONSE, String> responseWritingStrategy;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy  a strategy for user authentication
     * @param validator               a validator for user requests
     * @param requestConverter        a converter for retrieving POJO objects
     *                                from user requests
     * @param responseWritingStrategy a strategy for converting
     *                                a {@link RESPONSE response POJO}
     * */
    public UserRequestResponseHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, REQUEST> requestConverter,
        Function<RESPONSE, String> responseWritingStrategy
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.responseWritingStrategy = responseWritingStrategy;
    }

    @Override
    protected void afterProcess(Context request) {
        RESPONSE response = getResult(request);
        if (responseWritingStrategy != null && response != null) {
            request.result(responseWritingStrategy.apply(response));
        }
    }

    /**
     * A result setter.
     *
     * Is expected to be used in pair with {@link #getResult}
     *
     * @param context  a context for response persisting
     * @param response an object to be stored as the context
     *                 {@link #RESPONSE_CONTEXT_ATTRIBUTE_NAME} attribute
     * */
    protected void setResult(RESPONSE response, Context context) {
        context.attribute(RESPONSE_CONTEXT_ATTRIBUTE_NAME, response);
    }

    /**
     * A result getter
     *
     * Is expected to be used in pair with {@link #setResult}
     *
     * @param  context a context for extracting a response object
     * @return         a context attribute that is associated with
     *                 {@link #RESPONSE_CONTEXT_ATTRIBUTE_NAME} key
     * */
    protected RESPONSE getResult(Context context) {
        return context.attribute(RESPONSE_CONTEXT_ATTRIBUTE_NAME);
    }
}
