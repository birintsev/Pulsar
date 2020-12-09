package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic;

/**
 * Exception that indicates, that an element (e.g.
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult}
 * ) that was requested to find does not exist.
 * */
public class NotExistsException extends BusinessLogicException {

    /**
     * Constructs an {@link NotExistsException}
     * with the specified detail message
     *
     * @param message the detail message
     */
    public NotExistsException(String message) {
        super(message);
    }
}
