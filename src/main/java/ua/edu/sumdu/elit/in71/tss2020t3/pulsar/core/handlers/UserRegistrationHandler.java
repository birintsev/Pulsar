package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem.RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.Converter;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem;
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

    private final ApplicationConfiguration applicationConfiguration;

    private final Converter<UserRegistrationDTO, User> dtoConverter;

    private final Converter<String, UserRegistrationDTO> deserializer;

    private String unknownErrorResponse;

    private final UserService userService;

    /**
     * A default constructor
     *
     * @param   appConfig       a configuration for an application instance
     * @param   sessionFactory  a session factory that will be used
     *                          during input handling to persist statistic
     * */
    public UserRegistrationHandler(
        ApplicationConfiguration appConfig, SessionFactory sessionFactory
    ) {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        dtoConverter = new UserRegistrationDTOConverter();
        userService = new DatabaseUserService(sessionFactory);
        deserializer = new JSONString2UserDTOConverter();
        this.applicationConfiguration = appConfig;
        initializeUnknownErrorResponse();
    }

    @Override
    public void handle(@NotNull Context ctx) {
        UserRegistrationDTO dto;
        User user;
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
            ctx.status(HttpStatus.Code.BAD_REQUEST.getCode());
            ctx.result(
                buildErrorResponseOnUnserializableRequestBody(ctx.body())
            );
            return;
        }
        if (!isRequestValid(dto)) {
            LOGGER.error("Invalid registration request received: " + dto);
            ctx.status(HttpStatus.Code.BAD_REQUEST.getCode());
            ctx.result(buildErrorResponseOnInvalidRequest(dto));
            return;
        }
        user = userService.registerUser(
            dtoConverter.convert(dto)
        );
        ctx.status(HttpStatus.Code.OK.getCode());
        LOGGER.trace(
            "User (email=" + user.getId().getEmail()
                + ") has been registered registered"
        );
    }

    /**
     * Method only for encapsulation a sequence of actions for
     * {@link #unknownErrorResponse} initialization
     * */
    private void initializeUnknownErrorResponse() {
        ConfigurationItem unknownErrorResponseKey =
            RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI;
        try {
            this.unknownErrorResponse = IOUtils.toString(
                new URI(
                    applicationConfiguration.contains(unknownErrorResponseKey)
                        ? applicationConfiguration.get(unknownErrorResponseKey)
                        : unknownErrorResponseKey.getDefaultValue()
                ), Charset.defaultCharset()
            );
        } catch (URISyntaxException e) {
            LOGGER.error(e);
            throw new IllegalArgumentException(
                unknownErrorResponseKey
                    + " has invalid value in the configuration: "
                    + applicationConfiguration
                , e
            );
        } catch (IOException e) {
            LOGGER.error(e);
            throw new UncheckedIOException(e);
        }
    }

    private boolean isRequestValid(UserRegistrationDTO dto) {
        return validator.validate(dto).size() == 0;
    }

    private String buildErrorResponseOnInvalidRequest(
        UserRegistrationDTO invalidRequest
    ) {
        ErrorResponseTemplate response;
        Set<ConstraintViolation<UserRegistrationDTO>> constraintViolations =
            validator.validate(invalidRequest);
        if (constraintViolations.size() == 0) {
            LOGGER.warn(
                "Creating invalid response on valid user registration request"
            );
        }
        try {
            response = new ErrorResponseTemplate(
                constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toArray(String[]::new)
            );
            return new ObjectMapper().writeValueAsString(response);
        } catch (Exception e) {
            LOGGER.error("Unknown error", e);
            return unknownErrorResponse;
        }
    }

    private String buildErrorResponseOnUnserializableRequestBody(
        String requestBody
    ) {
        try {
            return new ObjectMapper().writeValueAsString(
                new ErrorResponseTemplate(
                    new String[] {
                        "Request was not recognized",
                        requestBody
                    }
                )
            );
        } catch (Exception e) {
            LOGGER.error("Unknown error", e);
            return unknownErrorResponse;
        }
    }

    /**
     * This is a class-template for simplification response building
     *
     * @see #buildErrorResponseOnUnserializableRequestBody
     * @see #buildErrorResponseOnInvalidRequest
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static final class ErrorResponseTemplate {
        private String[] messages;
    }
}
