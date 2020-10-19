package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

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
}
