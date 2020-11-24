package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import java.util.function.Function;
import javax.naming.AuthenticationException;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.JoinOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.HandlerValidator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This class represents a handler for {@link JoinOrganisationRequest requests}
 * */
public class JoinOrganisationHandler
extends HandlerValidator<JoinOrganisationRequest> {

    private static final Logger LOGGER = Logger.getLogger(
        JoinOrganisationHandler.class
    );

    private final AuthenticationStrategy authenticationStrategy;

    private final OrganisationService organisationService;

    /**
     * A default dependency injection constructor
     *
     * @param validator              a validator for request POJOs
     * @param bodyConverter          an object for
     *                               {@link Context#body() request} to
     *                               {@link JoinOrganisationRequest POJO}
     *                               converting
     * @param authenticationStrategy a strategy for {@link User} authentication
     * @param organisationService    a business-logic provider for
     *                               {@link JoinOrganisationRequest requests}
     *                               handling
     * */
    public JoinOrganisationHandler(
        Validator validator,
        Function<String, JoinOrganisationRequest> bodyConverter,
        AuthenticationStrategy authenticationStrategy,
        OrganisationService organisationService
    ) {
        super(validator, bodyConverter);
        this.authenticationStrategy = authenticationStrategy;
        this.organisationService = organisationService;
    }

    @Override
    public void handleValid(
        Context ctx,
        JoinOrganisationRequest joinOrganisationRequest
    ) {
        User user;
        Organisation organisation;
        try {
            user = authenticationStrategy.authenticate(ctx);
        } catch (AuthenticationException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.UNAUTHORIZED.getCode(),
                "Consider to check the credentials"
            );
        }
        organisation = organisationService.findById(
            new Organisation.ID(joinOrganisationRequest.getOrganisationId())
        );
        if (organisation == null) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "The organisation not found"
            );
        }
        if (organisationService.isMember(user, organisation)) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.OK.getCode(),
                "The user is a member of " + organisation.getName() + " already"
            );
        }
        organisationService.addToOrganisationMembers(user, organisation);
        throw new JsonHttpResponseException(
            HttpStatus.Code.OK.getCode(),
            "The user "
                + user.getUsername()
                + " has joined "
                + organisation.getName()
        );
    }
}
