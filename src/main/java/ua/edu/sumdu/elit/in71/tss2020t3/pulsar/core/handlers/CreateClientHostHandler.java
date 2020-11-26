package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

public class CreateClientHostHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        CreateClientHostHandler.class
    );

    private final UserService userService;

    private final ClientHostService clientHostService;

    private final Function<String, CreateClientHostDTO> bodyConverter;

    /**
     * A default constructor
     *
     * @param userService       a service for working with {@link User}s
     * @param clientHostService a service for working with {@link ClientHost}s
     * @param bodyConverter     a user request body converter
     * */
    public CreateClientHostHandler(
        UserService userService,
        ClientHostService clientHostService,
        Function<String, CreateClientHostDTO> bodyConverter
    ) {
        this.userService = userService;
        this.clientHostService = clientHostService;
        this.bodyConverter = bodyConverter;
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        CreateClientHostDTO dto;
        ClientHost newClientHost;
        try {
            dto = bodyConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(
                "Invalid request body for creating new client host: "
                    + ctx.body()
            );
            throw new BadRequestResponse("Invalid request body");
        }
        try {
            newClientHost = clientHostService.createForUserRequest(dto, user);
        } catch (AlreadyExistsException e) {
            LOGGER.error(e);
            throw new BadRequestResponse(
                "Either private or public key is already associated "
                    + "with another host"
            );
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            throw new BadRequestResponse("Bad request");
        } catch (ConstraintViolationException e) {
            LOGGER.error(
                "Error during creating new client host (invalid request)", e
            );
            Map<String, String> errors = new HashMap<>();
            e.getConstraintViolations().forEach(
                cv -> errors.put(
                    String.valueOf(cv.getInvalidValue()),
                    cv.getMessage()
                )
            );
            throw new BadRequestResponse(
                new ObjectMapper().writeValueAsString(errors)
            );
        }  catch (UserStatusException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                "The user has reached the created/subscribed client hosts limit"
            );
        }
        ctx.status(
            HttpStatus.Code.OK.getCode()
        );
        LOGGER.trace(
            "Registered new client host (public key = "
                + newClientHost.getPublicKey()
                + ") owner: " + user.getUsername()
        );
    }
}
