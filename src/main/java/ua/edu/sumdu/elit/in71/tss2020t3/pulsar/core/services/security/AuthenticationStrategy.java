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
     * */
    User authenticate(Context context) throws AuthenticationException;
}
