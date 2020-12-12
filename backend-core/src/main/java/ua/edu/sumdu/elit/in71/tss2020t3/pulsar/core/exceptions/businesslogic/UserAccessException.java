package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic;

import lombok.Getter;
import lombok.Setter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * A semantic wrapper for {@link BusinessLogicException}
 * informing that the {@link #user} does not have an access
 * to the {@link #accessedObject}
 * */
@Getter
@Setter
public class UserAccessException extends BusinessLogicException {

    private User user;

    private Object accessedObject;

    /**
     * An all-arguments-constructor for a new exception
     *
     * @param user a user who tried to reach an object
     * @param accessedObject an object to be reached by the user
     * @param message a detailed message for this exception
     * */
    public UserAccessException(
        User user,
        Object accessedObject,
        String message
    ) {
        super(message);
        this.user = user;
        this.accessedObject = accessedObject;
    }

    /**
     * An all-arguments-constructor for a new exception
     * with {@code null} message
     *
     * @param user a user who tried to reach an object
     * @param accessedObject an object to be reached by the user
     * */
    public UserAccessException(
        User user,
        Object accessedObject
    ) {
        super((String) null);
        this.user = user;
        this.accessedObject = accessedObject;
    }
}
