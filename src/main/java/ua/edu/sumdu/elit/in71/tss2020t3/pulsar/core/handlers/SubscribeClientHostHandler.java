package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.NoSuchElementException;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.SubscribeToClientHostRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * This handler handles requests to subscribe a
 * {@link User} to
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
 * */
@AllArgsConstructor
public class SubscribeClientHostHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        SubscribeClientHostHandler.class
    );

    private final UserService userService;

    private final ClientHostService clientHostService;

    private final Function<String, SubscribeToClientHostRequest>
        requestBodyConverter;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        SubscribeToClientHostRequest subscribeRequest;
        User user = userService.findByEmail(
            ctx.basicAuthCredentials().getUsername()
        );
        try {
            subscribeRequest = requestBodyConverter.apply(ctx.body());
        } catch (Exception e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                "Bad request. Expected a JSON object with a 'public_key' field"
            );
        }
        try {
            clientHostService.subscribeByPublicKey(
                subscribeRequest.getPublicKey(), user
            );
            LOGGER.trace(
                "User " + user.getId().getEmail()
                    + " has been subscribed to a ClientHost "
                    + "(publicKey = " + subscribeRequest.getPublicKey()
            );
        } catch (NoSuchElementException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.BAD_REQUEST.getCode(),
                subscribeRequest.getPublicKey() + " does not exist"
            );
        }
    }
}
