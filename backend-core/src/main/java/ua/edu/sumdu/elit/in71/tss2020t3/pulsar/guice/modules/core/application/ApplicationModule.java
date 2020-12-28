package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core.application;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMED;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_ADMIN_ACCOUNT;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import io.javalin.Javalin;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Handler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.InitialDataLoader;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.ExceptionHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.access.BasicAuthAccessManager;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.BasicAuthenticationStrategy;

/**
 * A Guice configuration module for application-related components
 * (e.g {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.Application})
 * */
public class ApplicationModule extends AbstractModule {

    @Provides
    Javalin javalin(
        AccessManager accessManager,
        @Named("NewClientHostStatisticHandler")
            Handler newClientHostStatisticHandler,
        @Named("UserRegistrationHandler")
            Handler userRegistrationHandler,
        @Named("RegistrationConfirmationHandler")
            Handler registrationConfirmationHandler,
        @Named("CreateClientHostHandler")
            Handler createClientHostHandler,
        @Named("GetClientHostStatisticHandler")
            Handler getClientHostStatisticHandler,
        @Named("ExceptionHandler")
            ExceptionHandler exceptionHandler,
        @Named("AuthenticationHandler")
            Handler authenticationHandler,
        @Named("GetUserClientHostsHandler")
            Handler getUserClientHostsHandler,
        @Named("UserRequestToResetPasswordHandler")
            Handler userRequestToResetPasswordHandler,
        @Named("UserResetPasswordHandler")
            Handler userResetPasswordHandler,
        @Named("SubscribeClientHostHandler")
            Handler subscribeClientHostHandler,
        @Named("UpdateUserStatusHandler")
            Handler updateUserStatusHandler,
        @Named("CreateOrganisationHandler")
            Handler createOrganisationHandler,
        @Named("JoinOrganisationHandler")
            Handler joinOrganisationHandler,
        @Named("GetOrganisationsHandler")
            Handler getOrganisationsHandler,
        @Named("GetAllClientHostsHandler")
            Handler getAllClientHostsHandler,
        @Named("GetAdminDashboardHandler")
            Handler getAdminDashboardHandler,
        @Named("CreateHttpAccessibilityCheckConfigurationRequestHandler")
            Handler createHttpAccessibilityCheckConfigurationRequestHandler,
        @Named("GetHttpAccessibilityCheckResultsRequestHandler")
            Handler getHttpAccessibilityCheckResultsRequestHandler,
        @Named("GetActiveTrackerUserHosts")
            Handler getActiveTrackerUserHosts,
        @Named("DeleteClientHost")
            Handler deleteClientHost,
        InitialDataLoader initialDataLoader
    ) {
        Set<Role> permittedRoles = new HashSet<>(
            Arrays.asList(
                new UserStatus(
                    System.getProperty(
                        USER_STATUS_REGISTRATION_CONFIRMED
                    )
                )
            )
        );
        Javalin javalin = Javalin.create(
            config -> {
                config
                    .accessManager(accessManager)
                    .addStaticFiles("/static")
                    .enableCorsForAllOrigins();
            }
        )
            .post(
                "/api/endpoint",
                newClientHostStatisticHandler
            )
            .post(
                "/registration",
                userRegistrationHandler
            )
            .get(
                "/registration-confirmation",
                registrationConfirmationHandler
            )
            .post(
                "/client-host/create",
                createClientHostHandler,
                permittedRoles
            )
            .get(
                "/client-host-statistic",
                getClientHostStatisticHandler,
                permittedRoles
            )
            .get(
                "/authentication",
                authenticationHandler
            )
            .get(
                "/user/client-hosts",
                getUserClientHostsHandler,
                permittedRoles
            )
            .post(
                "/reset-password/request",
                userRequestToResetPasswordHandler
            )
            .post(
                "/reset-password",
                userResetPasswordHandler
            )
            .post(
                "/client-host/subscribe",
                subscribeClientHostHandler,
                permittedRoles
            )
            .post(
                "/user/update-status",
                updateUserStatusHandler,
                permittedRoles
            )
            .post(
                "/organisation/create",
                createOrganisationHandler,
                permittedRoles
            )
            .post(
                "/organisation/join",
                joinOrganisationHandler,
                permittedRoles
            )
            .get(
                "/organisation",
                getOrganisationsHandler,
                permittedRoles
            )
            .get(
                "/client-host/all",
                getAllClientHostsHandler,
                permittedRoles
            )
            .get(
                "/admin/dashboard",
                getAdminDashboardHandler,
                Collections.singleton(
                    new UserStatus(USER_STATUS_ADMIN_ACCOUNT)
                )
            )
            .post(
                "/active-tracker/track",
                createHttpAccessibilityCheckConfigurationRequestHandler,
                permittedRoles
            )
            .get(
                "/active-tracker/results",
                getHttpAccessibilityCheckResultsRequestHandler,
                permittedRoles
            )
            .get(
                "/active-tracker/my-hosts",
                getActiveTrackerUserHosts,
                permittedRoles
            )
            .get(
                "/client-hosts/delete",
                deleteClientHost,
                permittedRoles
            )
            .exception(
                Exception.class,
                exceptionHandler
            );
        initialDataLoader.initDatabase();
        return javalin;
    }

    @Provides
    AuthenticationStrategy basicAuthenticationStrategy(
        UserService userService
    ) {
        return new BasicAuthenticationStrategy(userService);
    }

    @Provides
    AccessManager accessManager(UserService userService) {
        return new BasicAuthAccessManager(userService);
    }

    @Provides
    @Named("ExceptionHandler")
    ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }
}
