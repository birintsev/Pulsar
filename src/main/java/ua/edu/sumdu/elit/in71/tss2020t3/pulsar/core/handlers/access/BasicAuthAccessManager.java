package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.access;

import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * An implementation of {@link AccessManager} that works with requests
 * with basic authentication
 * */
public class BasicAuthAccessManager implements AccessManager {

    private static final Logger LOGGER = Logger.getLogger(
        BasicAuthAccessManager.class
    );

    private final UserService userService;

    /**
     * A default constructor
     *
     * @param userService a service that will be used during request managing
     * */
    public BasicAuthAccessManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void manage(
        @NotNull Handler handler,
        @NotNull Context ctx,
        @NotNull Set<Role> permittedRoles
    ) throws Exception {
        LOGGER.trace(
            "Request from " + ctx.req.getRemoteAddr()
                + " to " + ctx.req.getContextPath()
        );
        if (permittedRoles.isEmpty()) {
            handler.handle(ctx);
            return;
        }
        User user = getUser(ctx);
        Set<UserStatus> userRoles;
        String accessDeniedMessage = "Access denied";
        if (user == null) {
            throw new UnauthorizedResponse(accessDeniedMessage);
        }
        if (!passwordMatches(ctx, user)) {
            throw new UnauthorizedResponse(accessDeniedMessage);
        }
        userRoles = user.getUserStatuses();
        if (userRoles.stream().noneMatch(permittedRoles::contains)) {
            throw new UnauthorizedResponse(accessDeniedMessage);
        }
        handler.handle(ctx);
    }

    private User getUser(Context ctx) {
        if (!ctx.basicAuthCredentialsExist()) {
            return null;
        }
        return userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
    }

    /**
     * @return {@code true} if password from the context
     *         matches the {@code user} password
     * */
    private boolean passwordMatches(Context ctx, User user) {
        return user.getPassword().equals(
            ctx.basicAuthCredentials().getPassword()
        );
    }
}
