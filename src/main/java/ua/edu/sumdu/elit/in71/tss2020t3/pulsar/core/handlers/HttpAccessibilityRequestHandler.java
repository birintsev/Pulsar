package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javalin.http.Context;
import java.util.function.Function;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.PingRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.HttpAccessibilityCheckDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * A handler for requests to check some host availability by HTTP/HTTPS
 *
 * @see HttpAccessibilityCheck
 * @see AccessibilityService
 * */
public class HttpAccessibilityRequestHandler
extends UserRequestHandler<PingRequest> {

    private static final Logger LOGGER = Logger.getLogger(
        PingRequest.class
    );

    private final AccessibilityService accessibilityService;

    private final Function<HttpAccessibilityCheckDTO, String> responseConverter;

    private final ModelMapper modelMapper;

    /**
     * A default constructor for dependency injection
     * @param authenticationStrategy a strategy for user authentication
     * @param validator              a validator which will be used
     *                               for the {@link PingRequest POJO} validation
     * @param requestConverter       a {@link PingRequest request} converter
     * @param accessibilityService   a business logic provider for checking
     *                               hosts accessibility
     * @param responseConverter      a converter for a response POJO
     * @param modelMapper            a mapper for converting
     *                               business-layer POJOs
     *                               to their representation-layer analogues
     * */
    public HttpAccessibilityRequestHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, PingRequest> requestConverter,
        AccessibilityService accessibilityService,
        Function<HttpAccessibilityCheckDTO, String> responseConverter,
        ModelMapper modelMapper
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.accessibilityService = accessibilityService;
        this.responseConverter = responseConverter;
        this.modelMapper = modelMapper;
    }

    @Override
    public void handleUserRequest(
        PingRequest pingRequest,
        User requester,
        Context context
    ) {
        HttpAccessibilityCheck httpAccessibilityCheck =
            accessibilityService.checkAccess(
                pingRequest.getTargetHost(),
                pingRequest.getConnectionTimeout(),
                requester
            );
        Response response = modelMapper.map(
            httpAccessibilityCheck,
            Response.class
        );
        context.result(
            responseConverter.apply(response)
        );
    }

    @AllArgsConstructor
    @JsonIgnoreProperties({"user", "request_id", "timeout_cron_units"})
    public static final class Response extends HttpAccessibilityCheckDTO {
    }
}
