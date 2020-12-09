package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.activetracker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.javalin.http.Context;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker.GetHttpAccessibilityCheckResultsRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.activetracker.HttpAccessibilityCheckResultDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.JsonHttpResponseException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.NotExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.UserRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

public class GetHttpAccessibilityCheckResultsRequestHandler extends
UserRequestHandler<GetHttpAccessibilityCheckResultsRequest> {

    public static final String START_DATE_QUERY_PARAM_NAME = "start_date";

    public static final String END_DATE_QUERY_PARAM_NAME = "end_date";

    public static final String TARGET_URL_QUERY_PARAM_NAME = "target_url";

    private static final Logger LOGGER = Logger.getLogger(
        GetHttpAccessibilityCheckResultsRequestHandler.class
    );

    private final ModelMapper modelMapper;

    private final HttpAccessibilityService accessibilityService;

    private final Function<Object, String> defaultResponseWriterStrategy;

    /**
     * A default constructor for dependency injection
     *
     * @param authenticationStrategy        a strategy for user authentication
     * @param validator                     a validator for user requests
     * @param requestConverter              a user request converter
     * @param modelMapper                   a mapper for converting
     *                                      business-logic level entities
     *                                      to their representation-layer
     *                                      analogues
     * @param accessibilityService          a business-logic provider
     *                                      for accessibility checks management
     * @param defaultResponseWriterStrategy a default strategy for converting
     *                                      representation-layer entities
     * */
    public GetHttpAccessibilityCheckResultsRequestHandler(
        AuthenticationStrategy authenticationStrategy,
        Validator validator,
        Function<Context, GetHttpAccessibilityCheckResultsRequest>
            requestConverter,
        ModelMapper modelMapper,
        HttpAccessibilityService accessibilityService,
        Function<Object, String> defaultResponseWriterStrategy
    ) {
        super(authenticationStrategy, validator, requestConverter);
        this.modelMapper = modelMapper;
        this.accessibilityService = accessibilityService;
        this.defaultResponseWriterStrategy = defaultResponseWriterStrategy;
    }

    @Override
    public void handleUserRequest(
        GetHttpAccessibilityCheckResultsRequest request,
        User requester,
        Context context
    ) {
        HttpAccessibilityCheckConfiguration configuration =
            accessibilityService.findByTargetUrl(request.getTargetUrl());
        if (configuration == null) {
            throw new JsonHttpResponseException(
                HttpStatus.Code.NOT_FOUND.getCode(),
                request.getTargetUrl() + " is not tracked"
            );
        }
        try {
            context.result(
                defaultResponseWriterStrategy.apply(
                    modelMapper.map(
                        accessibilityService.findForUser(
                            requester,
                            configuration,
                            request.getStartDate(),
                            request.getEndDate()
                        ),
                        new TypeToken<List<Response>>() {
                        }.getType()
                    )
                )
            );
        } catch (NotExistsException e) {
            LOGGER.error(e);
            throw new JsonHttpResponseException(
                HttpStatus.Code.FORBIDDEN.getCode(),
                requester.getUsername()
                    + " has not submitted a request to track "
                    + request.getTargetUrl()
                    + " yet"
            );
        }
    }

    @AllArgsConstructor
    public static final class RequestConverter
    implements Function<Context, GetHttpAccessibilityCheckResultsRequest> {

        private final Function<String, ZonedDateTime> dateTimeReader;

        @Override
        public GetHttpAccessibilityCheckResultsRequest apply(Context context) {
            GetHttpAccessibilityCheckResultsRequest request =
                new GetHttpAccessibilityCheckResultsRequest();
            request.setStartDate(
                dateTimeReader.apply(
                    context.queryParam(START_DATE_QUERY_PARAM_NAME)
                )
            );
            request.setEndDate(
                dateTimeReader.apply(
                    context.queryParam(END_DATE_QUERY_PARAM_NAME)
                )
            );
            try {
                request.setTargetUrl(
                    new URL(
                        context.queryParam(TARGET_URL_QUERY_PARAM_NAME)
                    )
                );
            } catch (MalformedURLException e) {
                LOGGER.error(e);
                throw new IllegalArgumentException(e);
            }
            return request;
        }
    }

    @JsonIgnoreProperties("configuration")
    private static final class Response
    extends HttpAccessibilityCheckResultDTO {
    }
}
