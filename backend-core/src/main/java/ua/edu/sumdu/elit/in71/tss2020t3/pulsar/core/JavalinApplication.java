package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import io.javalin.Javalin;
import org.apache.log4j.Logger;

/**
 * A default {@link Application} implementation based on
 * <a href="https://javalin.io/">Javalin</a> framework
 *
 * @see    Application
 * @author Mykhailo Birintsev
 * */
public class JavalinApplication implements Application {

    private final Javalin app;

    private static final Logger LOGGER = Logger.getLogger(
        JavalinApplication.class
    );

    /**
     * A default dependency injection constructor
     *
     * @param javalin an instance of pre-configured {@link Javalin} server
     * */
    public JavalinApplication(Javalin javalin) {
        app = javalin;
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
}
