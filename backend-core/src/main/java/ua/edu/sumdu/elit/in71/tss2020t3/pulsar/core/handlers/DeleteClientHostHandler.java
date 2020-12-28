package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import java.util.function.Function;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.NotExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserAccessException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * Handles user requests to remove
 * a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
 * */
public class DeleteClientHostHandler
extends UserRequestHandler<DeleteClientHostHandler.Request> {

    private static final Logger LOGGER = Logger.getLogger(
        DeleteClientHostHandler.class
    );

    private final ClientHostService clientHostService;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator for user requests
     * @param requestConverter       a converter for retrieving POJO objects
     *                               from user requests
     * @param clientHostService      a service for managing client hosts
     * */
    public DeleteClientHostHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, Request> requestConverter,
        ClientHostService clientHostService
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.clientHostService = clientHostService;
    }

    @Override
    public void handleUserRequest(
        Request request,
        User requester,
        Context context
    ) {
        try {
            clientHostService.removeByRequest(
                requester,
                request.getPrivateKey()
            );
        } catch (UserAccessException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "You are not authorized to delete this client host"
            );
        } catch (NotExistsException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.NOT_FOUND.getCode(),
                "Client host (private key = "
                    + request.getPrivateKey()
                    + ") has not been found"
            );
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Request {

        private String privateKey;
    }

    public static final class RequestConverter
    implements Function<Context, Request> {

        @Override
        public Request apply(Context context) {
            return new Request(context.queryParam("private_key"));
        }
    }
}
