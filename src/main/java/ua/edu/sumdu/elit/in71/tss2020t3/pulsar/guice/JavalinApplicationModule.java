package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.DATABASE_TIMEZONE;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.javalin.Javalin;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Handler;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.InitialDataLoader;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.CPUInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatistic2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatisticFromDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.DiskInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JSONString2CreateClientHostConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.MemoryInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.NetworkInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.templates.DefaultJsonConversionStrategy;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.JoinOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.SubscribeToClientHostRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UpdateUserStatusDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRequestToResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.MemoryInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.AuthenticationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.CreateClientHostHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.CreateOrganisationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.ExceptionHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.GetAllClientHostsHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.GetClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.JoinOrganisationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.NewClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.RegistrationConfirmationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.SubscribeClientHostHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UpdateUserStatusHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRegistrationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRequestToResetPasswordHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserResetPasswordHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.access.BasicAuthAccessManager;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.SMTPService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.AuthenticationStrategy;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.security.BasicAuthenticationStrategy;

/**
 * A Guice injection configuration class for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core} classes
 * dependencies configuration
 * */
public class JavalinApplicationModule extends AbstractModule {

    @Provides
    @Singleton
    SessionFactory sessionFactory() {
        Configuration hibernateConfig = new Configuration();
        hibernateConfig
            .configure()
            .addAnnotatedClass(Organisation.class)
            .setProperty(
                "hibernate.connection.driver_class",
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_DRIVER_CLASS
                )
            )
            .setProperty(
                "hibernate.dialect",
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_DIALECT
                )
            )
            .setProperty(
                "hibernate.connection.password",
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_PASSWORD
                )
            )
            .setProperty(
                "hibernate.connection.username",
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_USER
                )
            )
            .setProperty(
                "hibernate.connection.url",
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_URL
                )
            ).setProperty(
                "hibernate.jdbc.time_zone",
                System.getProperty(DATABASE_TIMEZONE)
            );
        return hibernateConfig.buildSessionFactory();
    }

    @Provides
    UserService userService(SessionFactory sessionFactory) {
        return new DatabaseUserService(sessionFactory);
    }

    @Provides
    ClientHostService clientHostService(
        UserService userService,
        SessionFactory sessionFactory,
        Validator validator
    ) {
        return new ClientHostServiceImpl(
            userService,
            sessionFactory,
            validator
        );
    }

    @Provides
    Function<ClientHostStatisticDTO, ClientHostStatistic>
    clientHostStatisticConverter(
        ClientHostService clientHostService,
        Validator validator
    ) {
        return new ClientHostStatisticFromDTOConverter(
            clientHostService,
            validator
        );
    }

    @Provides
    Function<String, CreateClientHostDTO> createClientHostDTOConverter() {
        return new JSONString2CreateClientHostConverter();
    }

    @Provides
    ClientHostStatisticService clientHostStatisticService(
    SessionFactory sessionFactory
    ) {
        return new ClientHostStatisticServiceImpl(sessionFactory);
    }

    @Provides
    OrganisationService organisationService(
        SessionFactory sessionFactory,
        UserService userService
    ) {
        return new OrganisationServiceImpl(
            sessionFactory,
            userService
        );
    }

    @Provides
    Function<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> cpu2DTOConverter() {
        return new CPUInfo2DTOConverter();
    }

    @Provides
    Function<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
    memory2DTOConverter() {
        return new MemoryInfo2DTOConverter();
    }

    @Provides
    Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
    network2DTOConverter() {
        return new NetworkInfo2DTOConverter();
    }

    @Provides
    Function<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
    disk2DTOConverter() {
        return new DiskInfo2DTOConverter();
    }

    @Provides
    Function<String, JoinOrganisationRequest>
    joinOrganisationRequestConverter() {
        return new DefaultJsonConversionStrategy<>(
            JoinOrganisationRequest.class
        );
    }

    @Provides
    Function<ClientHostStatistic, ClientHostStatisticDTO>
    statistic2DTOConverter(
        Function<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> cpu2DTOConverter,
        Function<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
            memory2DTOConverter,
        Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
            network2DTOConverter,
        Function<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
            disk2DTOConverter
    ) {
        return new ClientHostStatistic2DTOConverter(
            cpu2DTOConverter,
            disk2DTOConverter,
            network2DTOConverter,
            memory2DTOConverter
        );
    }

    @Provides
    AccessManager accessManager(UserService userService) {
        return new BasicAuthAccessManager(userService);
    }

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
    @Named("ExceptionHandler")
    ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }

    @Provides
    MailService mailService() {
        return new SMTPService();
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
    Function<String, CreateOrganisationRequest>
    createOrganisationRequestBodyConverter() {
        return body -> {
            try {
                return new ObjectMapper().readValue(
                    body, CreateOrganisationRequest.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, UserResetPasswordDTO>
    userResetPasswordDTOConverter() {
        return string -> {
            try {
                return new ObjectMapper().readValue(
                    string, UserResetPasswordDTO.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, UserRequestToResetPasswordDTO>
    userRequestToResetPasswordDTOConverter() {
        return string -> {
            try {
                return new ObjectMapper().readValue(
                    string, UserRequestToResetPasswordDTO.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, SubscribeToClientHostRequest>
    subscribeToClientHostRequestConverter() {
        return s -> {
            try {
                return new ObjectMapper()
                    .readValue(s, SubscribeToClientHostRequest.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
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
    Function<String, UpdateUserStatusDTO> updateUserStatusDTOConverter() {
        return s -> {
            try {
                return new ObjectMapper()
                    .readValue(s, UpdateUserStatusDTO.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    AuthenticationStrategy basicAuthenticationStrategy(
        UserService userService
    ) {
        return new BasicAuthenticationStrategy(userService);
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
    InitialDataLoader initialDataLoader(SessionFactory sessionFactory) {
        return new InitialDataLoader(sessionFactory, false);
    }

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
        @Named("GetAllClientHostsHandler")
            Handler getAllClientHostsHandler,
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
            ).post(
                "/registration",
                userRegistrationHandler
            ).get(
                "/registration-confirmation",
                registrationConfirmationHandler
            ).post(
                "/client-host/create",
                createClientHostHandler,
                permittedRoles
            ).get(
                "/client-host-statistic",
                getClientHostStatisticHandler,
                permittedRoles
            ).get(
                "/authentication",
                authenticationHandler
            ).get(
                "/client-hosts",
                getAllClientHostsHandler,
                permittedRoles
            )
            .post(
                "/reset-password/request",
                userRequestToResetPasswordHandler
            )
            .post(
                "/reset-password",
                userResetPasswordHandler
            ).post(
                "/client-host/subscribe",
                subscribeClientHostHandler,
                permittedRoles
            ).post(
                "/user/update-status",
                updateUserStatusHandler,
                permittedRoles
            ).post(
                "/organisation/create",
                createOrganisationHandler,
                permittedRoles
            ).post(
                "/organisation/join",
                joinOrganisationHandler,
                permittedRoles
            ).exception(
                Exception.class,
                exceptionHandler
            );
        initialDataLoader.initDatabase();
        return javalin;
    }

    @Provides
    Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(ClientHost.class, ClientHostDTO.class).addMapping(
            source -> source.getId().getPrivateKey(),
            ClientHostDTO::setPrivateKey
        );
        return modelMapper;
    }

    @Provides
    @Named(value = "GetAllClientHostsHandler")
    Handler getAllClientHostsHandler(
        UserService userService,
        ClientHostService clientHostService,
        ModelMapper modelMapper
    ) {
        return new GetAllClientHostsHandler(
            userService,
            clientHostService,
            modelMapper
        );
    }

    @Provides
    @Named(value = "AuthenticationHandler")
    Handler authenticationHandler(UserService userService) {
        return new AuthenticationHandler(userService);
    }
}
