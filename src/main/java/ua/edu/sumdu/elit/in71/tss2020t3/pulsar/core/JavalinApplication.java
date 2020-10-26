package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import io.javalin.Javalin;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.NewClientHostStatisticHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.RegistrationConfirmationHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.UserRegistrationHandler;

/**
 * A default {@link Application} implementation based on
 * <a href="https://javalin.io/">Javalin</a> framework
 *
 * @see     Application
 * @see     ApplicationConfiguration
 * @author  Mykhailo Birintsev
 * */
public class JavalinApplication implements Application {

    private final Javalin app;

    private final ApplicationConfiguration appConfig;

    private final SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(
        JavalinApplication.class
    );

    /**
     * @param appCfg a set of effective application properties
     *                  that will be used during startup and in runtime
     * */
    public JavalinApplication(ApplicationConfiguration appCfg) {
        this.appConfig = appCfg;
        sessionFactory = createSessionFactory(appCfg);
        app = createApp(appCfg, sessionFactory);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void start() {
        app.start(
            Integer.parseInt(appConfig.get(ConfigurationItem.SERVER_PORT))
        );
    }

    private Javalin createApp(
        ApplicationConfiguration appCfg, SessionFactory sesFact
    ) {
        Javalin javalin = Javalin.create(config -> config.addStaticFiles("/static"))
            .post(
                "/api/endpoint",
                new NewClientHostStatisticHandler(sesFact)
            ).post(
                "/registration",
                new UserRegistrationHandler(appCfg, sesFact)
            ).get(
                "/registration-confirmation",
                new RegistrationConfirmationHandler(sesFact)
            );
        return javalin;
    }

    private SessionFactory createSessionFactory(
        ApplicationConfiguration appCfg
    ) {
        Configuration hibernateConfig = new Configuration();
        hibernateConfig.configure()
            .setProperty(
                "hibernate.connection.driver_class",
                appCfg.get(ConfigurationItem.DATABASE_DRIVER_CLASS)
            )
            .setProperty(
                "hibernate.dialect",
                appCfg.get(ConfigurationItem.DATABASE_DIALECT)
            )
            .setProperty(
                "hibernate.connection.password",
                appCfg.get(ConfigurationItem.DATABASE_PASSWORD)
            )
            .setProperty(
                "hibernate.connection.username",
                appCfg.get(ConfigurationItem.DATABASE_USER)
            )
            .setProperty(
                "hibernate.connection.url",
                appCfg.get(ConfigurationItem.DATABASE_URL)
            );
        return hibernateConfig.buildSessionFactory();
    }
}
