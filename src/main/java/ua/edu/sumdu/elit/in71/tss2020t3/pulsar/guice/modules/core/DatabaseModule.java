package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.DATABASE_TIMEZONE;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.InitialDataLoader;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;

/**
 * A Guice configuration module for database-related components
 * */
public class DatabaseModule extends AbstractModule {

    @Provides
    @Singleton
    SessionFactory sessionFactory() {
        Configuration hibernateConfig = new Configuration();
        hibernateConfig
            .configure()
            .addAnnotatedClass(Organisation.class)
            .addAnnotatedClass(HttpAccessibilityCheck.class)
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
    InitialDataLoader initialDataLoader(SessionFactory sessionFactory) {
        return new InitialDataLoader(sessionFactory, false);
    }
}
