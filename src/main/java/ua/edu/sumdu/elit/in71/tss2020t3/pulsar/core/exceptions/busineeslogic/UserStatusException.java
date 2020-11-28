package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.busineeslogic;

/**
 * This exception indicates that an operation that is being performed
 * for
 * a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User user}
 * requires another
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus status}
 * being associated with the
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User user}
 * or vice versa -
 * the {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User user}
 * has status not suitable for the operation
 * */
public class UserStatusException extends BusinessLogicException {

    /**
     * A default constructor
     *
     * @param message a detail message
     * */
    public UserStatusException(String message) {
        super(message);
    }
}
