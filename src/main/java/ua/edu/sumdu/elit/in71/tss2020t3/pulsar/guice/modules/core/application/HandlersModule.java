package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core.application;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import io.javalin.http.Handler;
import java.util.function.Function;
import javax.validation.Validator;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.JoinOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.SubscribeToClientHostRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UpdateUserStatusDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRequestToResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.UserDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.AuthenticationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.CreateClientHostHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.CreateOrganisationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.GetUserClientHostsHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.GetClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.GetOrganisationsHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.JoinOrganisationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.NewClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.RegistrationConfirmationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.SubscribeClientHostHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UpdateUserStatusHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRegistrationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRequestToResetPasswordHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserResetPasswordHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;

/**
 * A Guice configuration module for endpoint {@link Handler handlers}
 * */
public class HandlersModule extends AbstractModule {

    @Provides
    @Named("NewClientHostStatisticHandler")
    Handler newClientHostStatisticHandler(
        SessionFactory sessionFactory,
        Function<ClientHostStatisticDTO, ClientHostStatistic> dtoConverter
    ) {
        return new NewClientHostStatisticHandler(sessionFactory, dtoConverter);
    }

    @Provides
    @Named("UserRegistrationHandler")
    Handler newClientHostStatisticHandler(SessionFactory sessionFactory) {
        return new UserRegistrationHandler(sessionFactory);
    }

    @Provides
    @Named("RegistrationConfirmationHandler")
    Handler registrationConfirmationHandler(SessionFactory sessionFactory) {
        return new RegistrationConfirmationHandler(sessionFactory);
    }

    @Provides
    @Named("CreateClientHostHandler")
    Handler createClientHostHandler(
        UserService userService,
        ClientHostService clientHostService,
        Function<String, CreateClientHostDTO> requestBodyConverter
    ) {
        return new CreateClientHostHandler(
            userService,
            clientHostService,
            requestBodyConverter
        );
    }

    @Provides
    @Named("GetClientHostStatisticHandler")
    Handler getClientHostStatisticHandler(
        UserService userService,
        ClientHostService clientHostService,
        ClientHostStatisticService clientHostStatisticService,
        Validator validator,
        Function<ClientHostStatistic, ClientHostStatisticDTO> responseConverter
    ) {
        return new GetClientHostStatisticHandler(
            userService,
            clientHostService,
            clientHostStatisticService,
            validator,
            responseConverter
        );
    }

    @Provides
    @Named("UserRequestToResetPasswordHandler")
    Handler userRequestToResetPasswordHandler(
        UserService userService,
        MailService mailService,
        Function<String, UserRequestToResetPasswordDTO> dtoConverter
    ) {
        return new UserRequestToResetPasswordHandler(
            userService,
            mailService,
            dtoConverter
        );
    }

    @Provides
    @Named("UserResetPasswordHandler")
    Handler userResetPasswordHandler(
        UserService userService,
        Validator validator,
        Function<String, UserResetPasswordDTO> dtoConverter
    ) {
        return new UserResetPasswordHandler(
            userService,
            dtoConverter,
            validator
        );
    }

    @Provides
    @Named("CreateOrganisationHandler")
    Handler createOrganisationHandler(
        UserService userService,
        OrganisationService organisationService,
        Function<String, CreateOrganisationRequest> bodyConverter,
        Validator validator
    ) {
        return new CreateOrganisationHandler(
            userService,
            organisationService,
            bodyConverter,
            validator
        );
    }

    @Provides
    @Named("SubscribeClientHostHandler")
    Handler subscribeClientHostHandler(
        UserService userService,
        ClientHostService clientHostService,
        Function<String, SubscribeToClientHostRequest> requestConverter
    ) {
        return new SubscribeClientHostHandler(
            userService,
            clientHostService,
            requestConverter
        );
    }

    @Provides
    @Named("UpdateUserStatusHandler")
    Handler updateUserStatusHandler(
        UserService userService,
        Function<String, UpdateUserStatusDTO> bodyConverter,
        Validator validator
    ) {
        return new UpdateUserStatusHandler(
            userService,
            bodyConverter,
            validator
        );
    }

    @Provides
    @Named("JoinOrganisationHandler")
    Handler joinOrganisationHandler(
        Validator validator,
        AuthenticationStrategy userAuthenticationStrategy,
        OrganisationService organisationService,
        Function<String, JoinOrganisationRequest> bodyConverter
    ) {
        return new JoinOrganisationHandler(
            validator,
            bodyConverter,
            userAuthenticationStrategy,
            organisationService
        );
    }

    @Provides
    @Named("GetOrganisationsHandler")
    Handler getOrganisationsHandler(
        OrganisationService organisationService,
        ModelMapper modelMapper
    ) {
        return new GetOrganisationsHandler(
            organisationService,
            modelMapper
        );
    }

    @Provides
    @Named(value = "GetUserClientHostsHandler")
    Handler getAllClientHostsHandler(
        UserService userService,
        ClientHostService clientHostService,
        ModelMapper modelMapper,
        OrganisationService organisationService
    ) {
        return new GetUserClientHostsHandler(
            userService,
            clientHostService,
            modelMapper,
            organisationService
        );
    }

    @Provides
    @Named(value = "AuthenticationHandler")
    Handler authenticationHandler(
        AuthenticationStrategy authenticationStrategy,
        ModelMapper modelMapper,
        Function<UserDTO, String> jsonWriterStrategy
    ) {
        return new AuthenticationHandler(
            modelMapper,
            jsonWriterStrategy,
            authenticationStrategy
        );
    }
}
