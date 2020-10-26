package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;

/**
 * A handler that provides support for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User}
 * registration activation
 *
 * Handles requests to activate user accounts after registration
 *
 * @see UserRegistrationConfirmation
 * */
public class RegistrationConfirmationHandler implements Handler {

    public static final String KEY_PATH_PARAMETER_NAME = "key";

    private static final Logger LOGGER = Logger.getLogger(
        RegistrationConfirmationHandler.class
    );

    private final UserService userService;

    /**
     * A default constructor
     *
     * @param sessionFactory a Hibernate {@link SessionFactory}
     *                       that will be used
     *                       during interaction with a database
     * */
    public RegistrationConfirmationHandler(
        SessionFactory sessionFactory
    ) {
        userService = new DatabaseUserService(sessionFactory);
    }

    @Override
    public void handle(@NotNull Context ctx) {
        String rawKey = ctx.queryParam(KEY_PATH_PARAMETER_NAME);
        if (rawKey == null) {
            throw new BadRequestResponse(
                "The '" + KEY_PATH_PARAMETER_NAME + "' parameter is absent"
            );
        }
        UUID key;
        UserRegistrationConfirmation userRegConf;
        try {
            key = UUID.fromString(rawKey);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            throw new BadRequestResponse("The key is not formatted correctly");
        }
        try {
            userRegConf = userService.getRegistrationConfirmationBy(key);
        } catch (NoSuchElementException e) {
            LOGGER.error("Passed key not found: " + key);
            throw new BadRequestResponse("Passed key not found");
        }
        userRegConf.setConfirmationDate(
            new Timestamp(System.currentTimeMillis())
        );
        userService.confirmUserRegistration(userRegConf);
        ctx.status(HttpStatus.Code.OK.getCode());
        LOGGER.trace("A user registration confirmed successfully");
    }
}
