package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic;

/**
 * This exception is a semantic wrapper on {@link Exception} class.
 * It should be used when an action to be performed (e.g., by a user)
 * can not be performed due to business logic limitations.
 * <p>
 * This type of exceptions should not be suppressed
 * and must be handled in order to keep an operation workflow
 * fit the requirements.
 *
 * @see UserStatusException
 * */
public abstract class BusinessLogicException extends Exception {

    /**
     * A default constructor with neither {@link Exception#getMessage() message}
     * nor {@link Exception#getCause()} cause} provided
     * */
    public BusinessLogicException() {
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message a detail message
     */
    public BusinessLogicException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception
     * with the specified detail message
     * and a cause of this exception
     *
     * @param message a detail message
     * @param cause   a cause of this exception
     */
    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause a cause of this exception or an exception being wrapped
     * @see         Exception#Exception(Throwable)
     * */
    public BusinessLogicException(Throwable cause) {
        super(cause);
    }
}
