package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.util.Arrays;
import java.util.NoSuchElementException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.Main;

/**
 * This is an enum that represents a configurable item
 * for an application instance
 *
 * @author Mykhailo Birintsev
 * */
public enum ConfigurationItem {

    /**
     * Represents a port which an application instance will use
     * */
    SERVER_PORT("pulsar.server.port", "19991"),

    /**
     * Represents a database connection URL
     * */
    DATABASE_URL("pulsar.database.connection.url", null),

    /**
     * Represents a username which will be used when connecting to a database
     * */
    DATABASE_USER("pulsar.database.connection.user", null),

    /**
     * Represents a password of a database user profile
     * which will be used when connecting to a database
     * */
    DATABASE_PASSWORD("pulsar.database.connection.password", null),

    /**
     * Represents a JDBC connection driver class
     * which will be used when interacting with a database
     * */
    DATABASE_DRIVER_CLASS("pulsar.database.connection.driver_class", null),

    /**
     * Represents a dialect class, which will be used by Hibernate framework
     * <p>
     * See more <a href="https://hibernate.org/orm/documentation">here</a>
     * */
    DATABASE_DIALECT("pulsar.database.dialect", null),

    /**
     * Represents a root directory for file-based log files
     * */
    LOG_DIRECTORY(
        "pulsar.log.directory", Main.getRunningDirectory().getAbsolutePath()
    ),

    /**
     * Represents a {@link java.net.URI} of resource that contains a response
     * (in a {@link String} representation) that will be used each time
     * an unknown error happens and an application instance
     * is not able to handle the error
     * */
    RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI(
        "pulsar.response.unknown-error",
        "classpath:static/unknown-internal-server-error.json"
    );

    /**
     * Returns a {@link ConfigurationItem} that represents passed property
     *
     * @param       property                a property name
     * @return                              an instance of
     *                                      {@link ConfigurationItem}
     *                                      that stands for passed property
     * @exception   NullPointerException    if passed {@code property}
     *                                      is {@code null}
     * @exception   NoSuchElementException  if there is not any
     *                                      {@link ConfigurationItem}
     *                                      representing passed property
     * */
    public static ConfigurationItem valueOfProperty(String property) {
        return Arrays.stream(values())
            .filter(ci -> property.equals(ci.propertyName))
            .findFirst()
            .orElseThrow(
                () -> new NoSuchElementException(
                    "There is not any ConfigurationItem for" + property
                )
            );
    }

    /**
     * A name of this configuration item
     * configured in a {@code .properties} file
     *
     * @see java.util.Properties
     * @see PropertyBasedAppConfig
     * @see PropertiesAppConfigService
     * */
    private final String propertyName;

    private final String defaultValue;

    ConfigurationItem(String propertyName, String defaultValue) {
        this.propertyName = propertyName;
        this.defaultValue = defaultValue;
    }

    /**
     * This method is created to support {@code .property}-based configuration
     * of an application instance
     *
     * @return a name of a property
     * */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * A getter for the default value of a {@link ConfigurationItem}
     *
     * @return  a default value for a {@link ConfigurationItem}
     *          or {@code null} if the {@link ConfigurationItem}
     *          does not have a default value
     * */
    public String getDefaultValue() {
        return defaultValue;
    }
}
