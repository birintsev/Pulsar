package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.databind.util.Converter;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.SMTPService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JSONString2UserDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.UserRegistrationDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRegistrationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * This class represents a controller for new user creation requests
 * (registration requests)
 * */
public class UserRegistrationHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        UserRegistrationHandler.class
    );

    private final Validator validator;

    private final Converter<UserRegistrationDTO, User> dtoConverter;

    private final Converter<String, UserRegistrationDTO> deserializer;

    private final UserService userService;

    private final MailService mailService;

    /**
     * A default constructor
     *
     * @param   sessionFactory  a session factory that will be used
     *                          during input handling to persist statistic
     * */
    public UserRegistrationHandler(SessionFactory sessionFactory) {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        dtoConverter = new UserRegistrationDTOConverter();
        userService = new DatabaseUserService(sessionFactory);
        deserializer = new JSONString2UserDTOConverter();
        this.mailService = new SMTPService();
    }

    @Override
    public void handle(@NotNull Context ctx) {
        UserRegistrationDTO dto;
        User user;
        BadRequestResponse badResponse;
        try {
            dto = deserializer.convert(ctx.body());
        } catch (Exception e) {
            LOGGER.error(
                "Error during deserialization "
                    + "request body (below): "
                    + System.lineSeparator()
                    + ctx.body()
                    + System.lineSeparator()
                    + "to UserRegistrationDTO"
                , e
            );
            badResponse = new BadRequestResponse(
                "The request body is not a valid registration request"
            );
            throw badResponse;
        }
        if (!isRequestValid(dto)) {
            LOGGER.error("Invalid registration request received: " + dto);
            badResponse = new BadRequestResponse();
            validator.validate(dto).forEach(constraintViolation ->
                badResponse.getDetails().put(
                String.valueOf(constraintViolation.getInvalidValue()),
                constraintViolation.getMessage()
            ));
            throw badResponse;
        }
        try {
            user = userService.registerUser(
                dtoConverter.convert(dto)
            );
            mailService.sendRegistrationConfirmationEmail(
                userService.getRegistrationConfirmationFor(user)
            );
        } catch (AlreadyExistsException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(HttpStatus.Code.BAD_REQUEST
                .getCode(),
                "A user with such mail or username already registered"
            );
        }
        ctx.status(HttpStatus.Code.OK.getCode());
        LOGGER.trace(
            "User (email=" + user.getId().getEmail()
                + ") has been registered registered"
        );
    }

    private boolean isRequestValid(UserRegistrationDTO dto) {
        return validator.validate(dto).size() == 0;
    }
}
