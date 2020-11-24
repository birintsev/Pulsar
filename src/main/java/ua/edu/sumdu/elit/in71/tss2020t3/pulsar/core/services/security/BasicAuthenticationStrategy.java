package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security;

import io.javalin.http.Context;
import javax.naming.AuthenticationException;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * A default implementation of {@link AuthenticationStrategy} based on
 * HTTP/HTTPS Basic Authentication
 * */
@AllArgsConstructor
public class BasicAuthenticationStrategy implements AuthenticationStrategy {

    private static final Logger LOGGER = Logger.getLogger(
        BasicAuthenticationStrategy.class
    );

    private final UserService userService;

    @Override
    public User authenticate(Context context) throws AuthenticationException {
        String email;
        String password;
        User user;
        if (!context.basicAuthCredentialsExist()) {
            throw new AuthenticationException(
                "Basic Authentication credentials do not exist"
            );
        }
        email = context.basicAuthCredentials().getUsername();
        password = context.basicAuthCredentials().getPassword();
        user = userService.findByEmail(email);
        if (user == null) {
            throw new AuthenticationException(
                "Basic Authentication credentials do not exist"
            );
        }
        if (!password.equals(user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        return user;
    }
}
