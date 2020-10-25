package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * This is a default implementation of {@link ApplicationConfiguration}
 * <p>
 * Provides a possibility to configure an application instance
 * using {@code .property}-formatted files
 *
 * @see     Properties
 * @see     ApplicationConfiguration
 * @author  Mykhailo Birintsev
 * @deprecated see details on package-level documentation
 * */
@Deprecated
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
        return Optional.ofNullable(
            applicationConfig.getProperty(configurationItem.getPropertyName())
        ).orElseThrow(
            () -> new NoSuchElementException(
                "This container does not have configured value for "
                    + configurationItem
            )
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

    @NotNull
    @Override
    public Iterator<ConfigurationItem> iterator() {
        return applicationConfig
            .stringPropertyNames().stream()
            .map(
                propertyName -> {
                    try {
                        return ConfigurationItem.valueOfProperty(propertyName);
                    } catch (NoSuchElementException e) {
                        return null;
                    }
                }
            )
            .filter(Objects::nonNull)
            .collect(Collectors.toSet())
            .iterator();
    }

    @Override
    public boolean contains(ConfigurationItem configurationItem) {
        return applicationConfig
            .stringPropertyNames()
            .contains(configurationItem.getPropertyName());
    }

    @Override
    public Set<ConfigurationItem> items() {
        Set<ConfigurationItem> configurationItems = new HashSet<>();
        for (ConfigurationItem configurationItem : this) {
            configurationItems.add(configurationItem);
        }
        return configurationItems;
    }
}
