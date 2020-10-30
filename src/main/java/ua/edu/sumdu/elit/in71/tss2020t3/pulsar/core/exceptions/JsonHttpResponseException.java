package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.HttpResponseException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * This is a wrapper class for {@link HttpResponseException}
 * that stores provided information (i.e. a message and a map with details)
 * as a JSON object
 * <p>
 * An instance of this class stores a JSON object in the {@code message} field
 * and leaves the {@code details} field empty.
 * It always returns {@link Collections#emptyMap()} on
 * {@link #getDetails()} invocation.
 * */
public class JsonHttpResponseException extends HttpResponseException {

    /**
     * Creates an instance of {@link JsonHttpResponseException}
     * with empty details map
     *
     * @param status  an HTTP response status
     * @param message a response message
     * */
    public JsonHttpResponseException(
        int status,
        String message
    ) {
        super(
            status,
            convertToJSON(
                status,
                Optional.ofNullable(message).orElse(""),
                Collections.emptyMap()
            ),
            Collections.emptyMap()
        );
    }

    /**
     * A default constructor
     *
     * @param status  an HTTP response status
     * @param message a response message
     * @param details a response details to show to a requester
     *                (will be passed as a separate JSON object field)
     * @see           HttpResponseException
     * */
    public JsonHttpResponseException(
        int status,
        String message,
        @NotNull Map<String, String> details
    ) {
        super(
            status,
            convertToJSON(
                status,
                Optional.ofNullable(message).orElse(""),
                details
            ),
            Collections.emptyMap()
        );
    }

    /**
     * A default constructor that takes an array of {@link ConstraintViolation}s
     * and converts it to {@code details} map (for a client reference).
     * <p>
     * In this case {@link ConstraintViolation#getInvalidValue()} is taken
     * as a {@code details} key
     * associated with {@link ConstraintViolation#getMessage()} value.
     *
     * @param status               an HTTP response status
     * @param message              a response message
     * @param constraintViolations a set of invalid values
     *                             to show to a client as a response
     * */
    public JsonHttpResponseException(
        int status,
        String message,
        @NotNull ConstraintViolation<?>...constraintViolations
    ) {
        super(
            status,
            convertToJSON(
                status,
                Optional.ofNullable(message).orElse(""),
                constraintViolations
            ),
            Collections.emptyMap()
        );
    }

    private static String convertToJSON(
        int status,
        @NotNull String message,
        ConstraintViolation<?>...constraintViolations
    ) {
        Map<String, String> details = new HashMap<>();
        Arrays.stream(constraintViolations)
            .forEach(
                cv -> details.put(
                    String.valueOf(cv.getInvalidValue()),
                    cv.getMessage()
                )
            );
        return convertToJSON(status, message, details);
    }

    private static String convertToJSON(
        int status, String message, Map<String, String> details
    ) {
        try {
            return new ObjectMapper()
                .writeValueAsString(
                    new MessageTemplate(status, message, details)
                );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static final class MessageTemplate {
        private int status;
        private String message;
        private Map<String, String> details;
    }
}
