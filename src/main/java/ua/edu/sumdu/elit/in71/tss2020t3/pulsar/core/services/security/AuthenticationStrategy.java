package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security;

import io.javalin.http.Context;
import javax.naming.AuthenticationException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * An authentication provider for a {@link User} {@link Context request}
 * */
public interface AuthenticationStrategy {

    /**
     * Authenticates a user by passed {@link Context request}
     *
     * @param  context                 a context of a user request
     * @return                         an authenticated instance of {@link User}
     * @throws AuthenticationException if an exception happens during
     *                                 the process of authentication
     *                                 (e.g. the {@link Context context}
     *                                 contains bad credentials
     *                                 or does not contain them at all)
     * @throws ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException
     *                                 if the user has not confirmed
     *                                 his/her account
     * @see                            ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
     * @see                            ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMED
     * */
    User authenticate(Context context) throws AuthenticationException;
}
