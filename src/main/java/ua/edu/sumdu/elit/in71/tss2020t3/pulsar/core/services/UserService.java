package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.UUID;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;

/**
 * This service-layer interface was designed to represent basic operations
 * on {@link User} objects
 * <p>
 * It is expected to be a common way to perform such operations as
 * creation, removing and other.
 *
 * @see     User
 * @author  Mykhailo Birintsev
 * */
public interface UserService {

    /**
     * Registers new user
     *
     * @param       user                        a user that has not been
     *                                          registered yet
     * @return                                  registered {@code user}
     * @exception   IllegalArgumentException    if passed {@code user}
     *                                          is not valid
     *                                          or it already exists
     * */
    User registerUser(User user);

    /**
     * Provides information whether a {@link User} with such {@code userID}
     * is active
     *
     * @param     userID                           a user identifier
     * @return                                     {@code true} if a user
     *                                             with such identifier exists
     *                                             and usable (i.e. it can
     *                                             perform actions,
     *                                             at least log in)
     * @exception java.util.NoSuchElementException if there is no {@link User}
     *                                             with such id
     * @see                                        #exists(User.UserID)
     * */
    boolean isActive(User.UserID userID);

    /**
     * Provides information whether a {@link User} with such {@code userID}
     * has been registered
     *
     * @param   userID  a user identifier
     * @return          {@code true} if a user with such identifier
     *                  has already been registered
     * @see             #isActive(User.UserID)
     * */
    boolean exists(User.UserID userID);

    /**
     * Returns a POJO that represents information
     * about {@link User} account registration confirmation
     *
     * @param     user                     a user whose information is requested
     * @return                             a POJO containing information about
     *                                     {@code user} registration status
     * @exception IllegalArgumentException if passed user does not exist
     * @see                                UserRegistrationConfirmation
     * */
    UserRegistrationConfirmation getRegistrationConfirmationFor(User user);

    /**
     * Returns a POJO that represents information
     * about {@link User} account registration confirmation
     *
     * @param     id                               a primary key of the
     *                                             registration-related details
     * @exception java.util.NoSuchElementException if passed {@code id}
     *                                             does not exist
     * @return                                     a POJO containing information
     *                                             about a user registration
     *                                             status
     * @see                                        UserRegistrationConfirmation
     * */
    UserRegistrationConfirmation getRegistrationConfirmationBy(
        UserRegistrationConfirmation.ID id
    );

    /**
     * Returns a POJO that represents information
     * about {@link User} account registration confirmation
     *
     * @param     key                              a user registration
     *                                             confirmation key
     * @exception java.util.NoSuchElementException if passed {@code key}
     *                                             does not exist
     * @return                                     a POJO containing information
     *                                             about a user registration
     *                                             status
     * @see                                        UserRegistrationConfirmation
     * */
    UserRegistrationConfirmation getRegistrationConfirmationBy(UUID key);

    /**
     * Confirms user registration
     *
     * @param     confirmation          a JPA POJO representing information
     *                                  about {@link User} registration
     *                                  state. It should be valid.
     *                                  The 'valid' means its confirmation date
     *                                  is set and represents a future timestamp
     *                                  comparing with its registration date
     * @exception IllegalStateException if the {@code confirmation} is not valid
     * */
    void confirmUserRegistration(UserRegistrationConfirmation confirmation);
}
