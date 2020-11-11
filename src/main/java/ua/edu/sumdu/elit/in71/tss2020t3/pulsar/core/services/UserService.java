package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.UUID;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest;

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
     * @param     user                     a user that has not been
     *                                     registered yet
     * @return                             registered {@code user}
     * @exception IllegalArgumentException if passed {@code user}
     *                                     is not valid
     * @exception ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException
     *                                     if passed {@code user} already exists
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

    /**
     * Finds a user by an email
     *
     * @param  email a user mail address
     * @return       a user specified by passed {@code email}
     *               or {@code null} if such user does not exist
     * */
    User findByEmail(String email);

    /**
     * Finds a user by a username
     *
     * @param   username a {@code username} of a {@link User}
     * @return           a user specified by passed {@code username}
     *                   or {@code null} if such user does not exist
     * */
    User findByUsername(String username);

    /**
     * Finds latest unused request to reset a {@code user} password
     * <p>
     * The words 'latest unused request' mean,
     * that the {@code user} had requested to reset his/her password,
     * but has not done it yet.
     * That is, there is a key ({@link UserResetPasswordRequest#getResetKey()})
     * that could be used to reset {@code user}'s password.
     *
     * @param  user a {@link User} who has requested to reset the pasword
     * @return      an instance of {@link UserResetPasswordRequest} with empty
     *              {@link UserResetPasswordRequest#getResetWhen()} date
     *              or {@code null} if the {@code user} has not requested
     *              to reset his/her password
     *              or if the {@code user} does not exist
     * */
    UserResetPasswordRequest getLatestUnusedRequestOf(User user);

    /**
     * This method resets user's password
     * <p>
     *
     * @param     resetKey                 a key for password resetting
     * @param     newPassword              a new password value
     * @exception IllegalArgumentException if the {@code resetKey} is already
     *                                     used (i.e. associated
     *                                     {@link UserResetPasswordRequest}
     *                                     has non-{@code null}
     *                                     {@link UserResetPasswordRequest#getResetWhen()}
     *                                     value) or the {@code password}
     *                                     does not satisfy requirements
     *                                     to password format
     * */
    void resetPasswordByRequest(
        UUID resetKey, String newPassword
    );

    /**
     * This method creates a request to reset the password of a {@code user}.
     * <p>
     * In other words, this methods notes, that a user has requested to reset
     * his/her password. So, the {@code user} may update the password
     * using a {@link UserResetPasswordRequest#getResetKey()} as a one-time
     * credential for performing the authorized action.
     *
     * @param     user                     a {@link User} who raised a request
     * @return                             a container for
     *                                     {@link UserResetPasswordRequest#getResetKey()}
     *                                     using which the user may perform
     *                                     the authorized action
     *                                     of resetting the password.
     *                                     If such one (unused) already exists,
     *                                     this method will return it.
     * @exception IllegalArgumentException if the {@code user} does not exist
     * */
    UserResetPasswordRequest createResetPasswordRequestFor(User user);

    /**
     * Returns a {@link UserResetPasswordRequest}
     * associated with passed {@code resetKey}
     *
     * @param  resetKey a key for password resetting
     * @return          a {@link UserResetPasswordRequest} associated with
     *                  passed key or {@code null} if no results found
     * */
    UserResetPasswordRequest findByKey(UUID resetKey);
}
