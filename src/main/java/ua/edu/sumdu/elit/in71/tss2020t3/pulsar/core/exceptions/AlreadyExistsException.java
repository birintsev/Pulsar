package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions;

/**
 * Exception that indicates, that an element (e.g.
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
 * ) that was requested to create already exists
 * */
public class AlreadyExistsException extends IllegalArgumentException {

    /**
     * Constructs an {@link AlreadyExistsException}
     * with the specified detail message
     *
     * @param message the detail message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}
