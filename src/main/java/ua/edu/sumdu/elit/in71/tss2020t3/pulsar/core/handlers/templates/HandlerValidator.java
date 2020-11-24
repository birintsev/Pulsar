package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;

/**
 * This class contains a set of common validation-related actions on
 * {@link REQUEST requests} coming from a user by HTTP/HTTPS.
 * <p>
 * So, if you need to {@link javax.validation.Validator#validate validate}
 * a {@link REQUEST request} before its {@link Handler#handle handling},
 * just extend this class and implement the logic of handling a request in
 * {@link #handleValid the method}.
 * <p>
 * Below is a brief description of the actions it performs
 * before processing a request:
 * <ol>
 *     <li> {@link Function#apply Converts} the request body
 *          to a {@link REQUEST POJO} with {@link #bodyConverter}
 *     <li> Validates the {@link REQUEST POJO} with {@link #validator}
 *     <li> If the {@link REQUEST POJO} is valid,
 *          processes the {@link REQUEST POJO} with {@link #handleValid}
 *     <li> If the {@link REQUEST POJO} is NOT valid,
 *          throws a {@link JsonHttpResponseException} with
 *          {@link JsonHttpResponseException#getMessage() message}
 *          = "Bad request" and
 *          {@link JsonHttpResponseException#getDetails() details} containing
 *          {@link ConstraintViolation#getInvalidValue() invalid values}
 *          of the {@link REQUEST POJO} as keys
 *          and {@link ConstraintViolation#getMessage() messages} as values
 * </ol>
 * <p>
 * A {@link REQUEST request} is considered invalid if the {@link #validator}
 * produces non-empty set of {@link ConstraintViolation} after its validation.
 *
 * @param <REQUEST> a type requests handled by this handler
 * */
@Data
@AllArgsConstructor
public abstract class HandlerValidator<REQUEST> implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        HandlerValidator.class
    );

    private final Validator validator;

    private final Function<String, REQUEST> bodyConverter;

    /**
     * A method for the logic implementation
     * of the {@link REQUEST request} handling
     * after its {@link #validate validation}
     *
     * @param ctx     a request {@link Context context}
     * @param request a valid {@link REQUEST request}
     * */
    public abstract void handleValid(Context ctx, REQUEST request);

    @Override
    public final void handle(@NotNull Context ctx) {
        REQUEST request;
        try {
            request = bodyConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request format"
            );
        }
        validate(request);
        handleValid(ctx, request);
    }

    private void validate(REQUEST request) {
        Set<ConstraintViolation<REQUEST>> constraintViolations =
            validator.validate(request);
        if (!constraintViolations.isEmpty()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request",
                constraintViolations.toArray(new ConstraintViolation[0])
            );
        }
    }
}
