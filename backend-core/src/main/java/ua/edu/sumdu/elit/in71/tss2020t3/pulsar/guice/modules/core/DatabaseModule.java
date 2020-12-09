package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.DATABASE_TIMEZONE;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.InitialDataLoader;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Email;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.UserCheckConfigurationSubscription;

/**
 * A Guice configuration module for database-related components
 * */
public class DatabaseModule extends AbstractModule {

    public static final String HIBERNATE_CONNECTION_DRIVER_CLASS =
        "hibernate.connection.driver_class";

    public static final String HIBERNATE_DIALECT                 =
        "hibernate.dialect";

    public static final String HIBERNATE_CONNECTION_PASSWORD     =
        "hibernate.connection.password";

    public static final String HIBERNATE_CONNECTION_USERNAME     =
        "hibernate.connection.username";

    public static final String HIBERNATE_CONNECTION_URL          =
        "hibernate.connection.url";

    public static final String HIBERNATE_JDBC_TIME_ZONE          =
        "hibernate.jdbc.time_zone";

    @Provides
    @Singleton
    SessionFactory sessionFactory() {
        Configuration hibernateConfig = new Configuration();
        hibernateConfig
            .configure()
            .addAnnotatedClass(Organisation.class)
            .addAnnotatedClass(Email.class)
            .addAnnotatedClass(HttpAccessibilityCheckResult.class)
            .addAnnotatedClass(HttpAccessibilityCheckConfiguration.class)
            .addAnnotatedClass(UserCheckConfigurationSubscription.class)
            .setProperty(
                HIBERNATE_CONNECTION_DRIVER_CLASS,
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_DRIVER_CLASS
                )
            )
            .setProperty(
                HIBERNATE_DIALECT,
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_DIALECT
                )
            )
            .setProperty(
                HIBERNATE_CONNECTION_PASSWORD,
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_PASSWORD
                )
            )
            .setProperty(
                HIBERNATE_CONNECTION_USERNAME,
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_USER
                )
            )
            .setProperty(
                HIBERNATE_CONNECTION_URL,
                System.getProperty(
                    ApplicationPropertiesNames.DATABASE_URL
                )
            ).setProperty(
                HIBERNATE_JDBC_TIME_ZONE,
                System.getProperty(DATABASE_TIMEZONE)
        );
        return hibernateConfig.buildSessionFactory();
    }

    @Provides
    InitialDataLoader initialDataLoader(SessionFactory sessionFactory) {
        return new InitialDataLoader(sessionFactory, false);
    }
}
