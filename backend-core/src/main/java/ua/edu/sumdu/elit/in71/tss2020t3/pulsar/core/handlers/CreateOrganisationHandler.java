package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_PREMIUM_ACCOUNT;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * This is a handler for user requests to create an
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation}
 * */
@AllArgsConstructor
public class CreateOrganisationHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        CreateOrganisationHandler.class
    );

    private final UserService userService;

    private final OrganisationService organisationService;

    private final Function<String, CreateOrganisationRequest> bodyConverter;

    private final Validator validator;

    @Override
    public void handle(@NotNull Context ctx) {
        Organisation organisation;
        CreateOrganisationRequest request;
        User requester = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        if (!userService.isUserPremiumAccount(requester)) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "The user does not have PREMIUM_ACCOUNT status"
            );
        }
        try {
            request = bodyConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request"
            );
        }
        validate(request);
        try {
            organisation = organisationService.create(
                request.getName(),
                requester
            );
        } catch (UserStatusException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "Only users with "
                    + USER_STATUS_PREMIUM_ACCOUNT
                    + " status are allowed to create organisations"
            );
        }
        throw new JsonHttpResponseException(
            HttpStatus.Code.OK.getCode(),
            "The organisation \""
                + organisation.getName()
                + "\" has been created",
            Collections.singletonMap(
                "id",
                organisation.getId().getOrganisationId().toString()
            )
        );
    }

    // if request is not valid, an exception is thrown
    // if request is ok, nothing happens
    private void validate(CreateOrganisationRequest request) {
        Set<ConstraintViolation<CreateOrganisationRequest>> validationResult =
            validator.validate(request);
        if (!validationResult.isEmpty()) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request",
                validationResult.toArray(new ConstraintViolation[0])
            );
        }
    }
}
