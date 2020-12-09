package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_PREMIUM_ACCOUNT;

import io.javalin.http.Context;
import java.util.NoSuchElementException;
import java.util.function.Function;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.JoinOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This class represents a handler for {@link JoinOrganisationRequest requests}
 * */
public class JoinOrganisationHandler
extends UserRequestHandler<JoinOrganisationRequest> {

    private static final Logger LOGGER = Logger.getLogger(
        JoinOrganisationHandler.class
    );

    private final OrganisationService organisationService;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator for user requests POJOs
     * @param requestConverter       a converter for retrieving POJO objects
     *                               from user requests
     * @param organisationService    a business logic provider for
     *                               {@link Organisation}-related operations
     * */
    public JoinOrganisationHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, JoinOrganisationRequest> requestConverter,
        OrganisationService organisationService
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.organisationService = organisationService;
    }

    @Override
    public void handleUserRequest(
        JoinOrganisationRequest joinOrganisationRequest,
        User requester,
        Context context
    ) {
        try {
            organisationService.addToOrganisationMembers(
                requester,
                new Organisation.ID(
                    joinOrganisationRequest.getOrganisationId()
                )
            );
        } catch (UserStatusException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "Only users with "
                    + USER_STATUS_PREMIUM_ACCOUNT
                    + " status are allowed to be organisation members"
            );
        } catch (AlreadyExistsException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.OK.getCode(),
                "The user is already a member of the organisation"
            );
        } catch (NoSuchElementException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Organisation with id "
                    + joinOrganisationRequest.getOrganisationId()
                    + " does not exist"
            );
        }
        throw new JsonHttpResponseException(
            HttpStatus.Code.OK.getCode(),
            "The user "
                + requester.getUsername()
                + " has joined the organisation"
        );
    }
}
