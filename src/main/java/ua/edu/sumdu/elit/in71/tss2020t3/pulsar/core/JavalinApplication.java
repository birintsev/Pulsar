package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMED;

import com.fasterxml.jackson.databind.util.Converter;
import io.javalin.Javalin;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.Main;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatisticFromDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JSONString2CreateClientHostConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.CreateClientHostHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.NewClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.RegistrationConfirmationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRegistrationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.access.BasicAuthAccessManager;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * A default {@link Application} implementation based on
 * <a href="https://javalin.io/">Javalin</a> framework
 *
 * @see     Application
 * @author  Mykhailo Birintsev
 * */
public class JavalinApplication implements Application {

    private final Javalin app;

    private final SessionFactory sessionFactory;

    private final AccessManager accessManager;

    private static final Logger LOGGER = Logger.getLogger(
        JavalinApplication.class
    );

    /**
     * A default constructor
     *
     * @param properties a set of application properties that will be used
     *                   during startup and in runtime
     *                   (will be propagated to System-level, i.e.
     *                   {@link System#setProperty(String, String)})
     * */
    public JavalinApplication(Properties properties) {
        Main.updateSystemProperties(properties);
        sessionFactory = createSessionFactory();
        this.accessManager = new BasicAuthAccessManager(
            new DatabaseUserService(sessionFactory)
        );
        app = createApp(sessionFactory);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void start() {
        app.start(
            Integer.parseInt(
                System.getProperty(ApplicationPropertiesNames.SERVER_PORT)
            )
        );
        LOGGER.info("The application is started");
    }

    private Javalin createApp(SessionFactory sesFact) {
        Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();
        UserService userService = new DatabaseUserService(sesFact);
        ClientHostService clientHostService =
            new ClientHostServiceImpl(
                userService,
                sesFact,
                validator
            );
        Converter<ClientHostStatisticDTO, ClientHostStatistic>
            clientHostStatisticConverter =
            new ClientHostStatisticFromDTOConverter(
                clientHostService
            );
        Converter<String, CreateClientHostDTO> createClientHostDTOConverter =
            new JSONString2CreateClientHostConverter();
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
                    .addStaticFiles("/static");
            }
        )
            .post(
                "/api/endpoint",
                new NewClientHostStatisticHandler(
                    sesFact, clientHostStatisticConverter
                )
            ).post(
                "/registration",
                new UserRegistrationHandler(sesFact)
            ).get(
                "/registration-confirmation",
                new RegistrationConfirmationHandler(sesFact)
            ).post(
                "/client-host/create",
                new CreateClientHostHandler(
                    userService,
                    clientHostService,
                    createClientHostDTOConverter
                ),
                permittedRoles
            );
        return javalin;
    }

    private SessionFactory createSessionFactory() {
        Configuration hibernateConfig = new Configuration();
        hibernateConfig.configure()
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
            );
        return hibernateConfig.buildSessionFactory();
    }
}
