package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * This is a default implementation of {@link ApplicationConfiguration}
 * <p>
 * Provides a possibility to configure an application instance
 * using {@code .property}-formatted files
 *
 * @see     Properties
 * @see     ApplicationConfiguration
 * @author  Mykhailo Birintsev
 * */
public class PropertyBasedAppConfig implements ApplicationConfiguration {

    private static final int MIN_TCP_PORT_NUMBER = 0x1;

    private static final int MAX_TCP_PORT_NUMBER = 0xFFFF;

    private static final Logger LOGGER = Logger.getLogger(
        PropertyBasedAppConfig.class
    );

    private final Properties applicationConfig;

    /**
     * Creates an instance of {@link PropertyBasedAppConfig}
     * based on passed {@link Properties}
     *
     * @param applicationConfig a set of application properties
     *                          that will be used.
     *                          <strong>Note</strong>, that passed properties
     *                          are copied.
     * @see                     PropertyBasedAppConfig#copy(Properties)
     * */
    public PropertyBasedAppConfig(Properties applicationConfig) {
        if (!arePropertiesValid(applicationConfig)) {
            throw new IllegalArgumentException(
                "Passed application config is invalid:"
                    + System.lineSeparator() + applicationConfig
            );
        }
        this.applicationConfig = copy(applicationConfig);
    }

    @Override
    @Deprecated
    public String getDialect() {
        return applicationConfig.getProperty("pulsar.database.dialect");
    }

    @Override
    @Deprecated
    public int getPort() {
        return Integer.parseInt(
            applicationConfig.getProperty("pulsar.server.port")
        );
    }

    @Override
    public String toString() {
        return "PropertyBasedAppConfig{"
            + "applicationConfig=" + applicationConfig
            + '}';
    }

    @Override
    @Deprecated
    public String getDatabaseConnectionURL() {
        return applicationConfig.getProperty("pulsar.database.connection.url");
    }

    @Override
    @Deprecated
    public String getDatabaseUser() {
        return applicationConfig.getProperty("pulsar.database.connection.user");
    }

    @Override
    @Deprecated
    public String getDatabasePassword() {
        return applicationConfig.getProperty(
            "pulsar.database.connection.password"
        );
    }

    @Override
    @Deprecated
    public String getDatabaseConnectionDriver() {
        return applicationConfig.getProperty(
            "pulsar.database.connection.driver_class"
        );
    }

    @Override
    public String get(ConfigurationItem configurationItem) {
        return applicationConfig.getProperty(
            configurationItem.getPropertyName()
        );
    }

    private Properties copy(Properties properties) {
        Properties copy = new Properties();
        for (String key : properties.stringPropertyNames()) {
            copy.setProperty(key, properties.getProperty(key));
        }
        return copy;
    }

    private static String[] getRequiredPropertiesKeys() {
        return new String[] {
            "pulsar.database.connection.url",
            "pulsar.database.connection.user",
            "pulsar.database.connection.driver_class",
            "pulsar.server.port"
        };
    }

    private static boolean arePropertiesValid(Properties properties) {
        if (properties == null) {
            return false;
        }
        for (String property : getRequiredPropertiesKeys()) {
            if (properties.getProperty(property) == null) {
                return false;
            }
        }
        if (!isValidPortNumber(properties.getProperty("pulsar.server.port"))) {
            return false;
        }
        try {
            Class.forName(properties.getProperty("pulsar.database.dialect"));
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private static boolean isValidPortNumber(String portNumber) {
        int serverPort;
        try {
            serverPort = Integer.parseInt(portNumber);
        } catch (Exception e) {
            return false;
        }
        return serverPort >= MIN_TCP_PORT_NUMBER
            && serverPort <= MAX_TCP_PORT_NUMBER;
    }
}
