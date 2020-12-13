package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates;

import io.javalin.http.Context;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * An extension for {@link UserRequestResponseHandler}
 * that makes a process of converting business-level POJOs
 * to their representation-level analogues
 * ({@link DTO Data Transfer Objects}) easier
 * <p>
 * It's recommended to keep the steps below during this class implementation:
 * <ol>
 *     <li> Do a handler-related actions for receiving
 *          a business-level {@link RESPONSE POJO} (or a set of POJOs)
 *     </li>
 *     <li>
 *          Set a result by <strong>{@link #convertAndSet}</strong>
 *     </li>
 *     <li>
 *          Done! Your response will automatically be
 *          <strong>{@code converted}</strong> to
 *          a {@link DTO representation-layer POJO}
 *          (using {@link #toDtoConvertingStrategy})
 *          and the result will be <strong>{@code set}</strong>
 *          for the {@link Context request}
 *     </li>
 * </ol>
 *
 * @param <REQUEST>  a POJO representation of incoming request
 * @param <RESPONSE> a business-logic layer object
 *                   (or a collection of objects) requested in a request
 * @param <DTO>>     a representation-layer analogue of the {@link RESPONSE}
 * */
public abstract class ToDTOHandler<REQUEST, RESPONSE, DTO>
extends UserRequestResponseHandler<REQUEST, DTO> {

    private static final Logger LOGGER = Logger.getLogger(
        UserRequestResponseHandler.class
    );

    private final Function<RESPONSE, DTO> toDtoConvertingStrategy;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy  a strategy for user authentication
     * @param validator               a validator for user requests
     * @param requestConverter        a converter for retrieving POJO objects
     *                                from user requests
     * @param responseWritingStrategy a strategy for converting
     *                                a {@link RESPONSE response POJO}
     * @param toDtoConvertingStrategy a converter that will be used
     *                                for converting a business-leve POJOs to
     *                                their representation-level analogues
     * */
    public ToDTOHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, REQUEST> requestConverter,
        Function<DTO, String> responseWritingStrategy,
        Function<RESPONSE, DTO> toDtoConvertingStrategy
    ) {
        super(
            authenticationStrategy,
            validator,
            requestConverter,
            responseWritingStrategy
        );
        this.toDtoConvertingStrategy = toDtoConvertingStrategy;
    }

    protected void convertAndSet(
        RESPONSE response,
        Context context
    ) {
        LOGGER.trace("converting " + response.getClass() + " to a DTO");
        super.setResult(
            toDtoConvertingStrategy.apply(response),
            context
        );
    }
}
