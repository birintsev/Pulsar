package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic;

/**
 * This exception is thrown when user authentication can not be completed by some reason(s).
 * Inheritors as
 * */
public class AuthenticationException extends Exception {

    /**
     * Constructs an {@link AuthenticationException}
     * with {@code null} cause and detail message
     */
    public AuthenticationException() {
        super();
    }

    /**
     * Constructs an {@link AuthenticationException}
     * with the specified detail message
     *
     * @param message the detail message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Constructs an {@link AuthenticationException}
     * with specified cause
     *
     * @param cause   a cause of this exception
     * @see   Exception#Exception(Throwable)
     */
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
