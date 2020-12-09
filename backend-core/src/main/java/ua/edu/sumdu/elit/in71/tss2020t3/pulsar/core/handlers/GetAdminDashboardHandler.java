package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.http.Context;
import java.math.BigInteger;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.templates.HandlerAuthenticator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AdminDashboardService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * This is a handler for retrieving such admin statistic as:
 * <ul>
 *     <li>the total number of users and active users</li>
 *     <li>the total number of client hosts and active client hosts</li>
 *     <li>the total number of emails sent</li>
 *     <li>the total number of active HTTP/HTTPS checks made</li>
 * </ul>
 * If need of other statistic data arises,
 * it should be provided in this handler.
 *
 * @see ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_ADMIN_ACCOUNT
 * */
public class GetAdminDashboardHandler extends HandlerAuthenticator {

    private static final Logger LOGGER = Logger.getLogger(
        GetAdminDashboardHandler.class
    );

    private final AdminDashboardService adminDashboardService;

    private final Function<Object, String> defaultResponseWriter;

    /**
     * A default constructor for dependencies injection
     * @param authenticationStrategy a strategy for user authentication
     * @param adminDashboardService  a service for statistics by users,
     *                               client hosts etc.
     * @param defaultResponseWriter  a default converter for
     *                               {@link Response response} entities
     * */
    public GetAdminDashboardHandler(
        AuthenticationStrategy authenticationStrategy,
        AdminDashboardService adminDashboardService,
        Function<Object, String> defaultResponseWriter
    ) {
        super(authenticationStrategy);
        this.adminDashboardService = adminDashboardService;
        this.defaultResponseWriter = defaultResponseWriter;
    }

    @Override
    public void handleAuthenticated(Context context, User user) {
        String stringResponse = defaultResponseWriter.apply(
            new Response(
                adminDashboardService.getTotalUsersNumber(),
                adminDashboardService.getActiveUsersNumber(),
                adminDashboardService.getTotalClientHostsNumber(),
                adminDashboardService.getActiveClientHostsNumber(),
                adminDashboardService.getTotalSentEmailsNumber(),
                adminDashboardService.getTotalActiveChecksNumber()
            )
        );
        LOGGER.info("Current admin dashboard statistic: " + stringResponse);
        context.result(stringResponse);
    }

    /**
     * Just a response template class
     * */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Response {

        @JsonProperty("total_users")
        private BigInteger totalUsers;

        @JsonProperty("active_users")
        private BigInteger activeUsers;

        @JsonProperty("total_client_hosts")
        private BigInteger allClientHosts;

        @JsonProperty("active_client_hosts")
        private BigInteger activeClientHosts;

        @JsonProperty("total_sent_letters")
        private BigInteger totalSentLetters;

        @JsonProperty("total_active_http_checks")
        private BigInteger totalActiveHttpChecks;
    }
}
