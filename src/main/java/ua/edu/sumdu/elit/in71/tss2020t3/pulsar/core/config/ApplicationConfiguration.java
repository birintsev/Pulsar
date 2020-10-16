package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.util.Set;

/**
 * This interface is designed to be a container of server configuration
 * which (probably) would be provided/represented in different ways
 * (e.g. in <i>.properties</i> file, <i>.xml</i> file, on the local drive
 * or via network).
 * <p>
 * Note that <strong>this class does not provide any information
 * about configuration validity</strong>. That is, it is no more
 * than just an abstraction, a wrapper.
 *
 * @see     ConfigurationItem
 * @author  Mykhailo Birintsev
 * */
public interface ApplicationConfiguration extends Iterable<ConfigurationItem> {

    /**
     * @return      an integer between 0 and 65535, representing port number
     *              that an application instance will use
     * @see         ConfigurationItem#SERVER_PORT
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    int getPort();

    /**
     * @return      a database connection URL set in configuration.
     *              For properties-based configuration is a value of
     *              {@code server.port} key.
     * @see         ConfigurationItem#DATABASE_URL
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    String getDatabaseConnectionURL();

    /**
     * @return      a database username set in configuration.
     *              For properties-based configuration is a value of
     *              {@code database.connection.user} key.
     * @see         ConfigurationItem#DATABASE_USER
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    String getDatabaseUser();

    /**
     * @return      a database user profile password set in configuration.
     *              For properties-based configuration is a value of
     *              {@code database.connection.password} key.
     * @see         ConfigurationItem#DATABASE_PASSWORD
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    String getDatabasePassword();

    /**
     * @return      a driver class name set in configuration.
     *              For properties-based configuration is a value of
     *              {@code database.connection.driver_class} key.
     * @see         ConfigurationItem#DATABASE_DRIVER_CLASS
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    String getDatabaseConnectionDriver();

    /**
     * @return      a dialect class name set in configuration.
     *              For properties-based configuration is a value of
     *              {@code database.dialect} key.
     * @see         ConfigurationItem#DATABASE_DIALECT
     * @deprecated  use {@link ApplicationConfiguration#get(ConfigurationItem)}
     *              instead
     * */
    @Deprecated
    String getDialect();

    /**
     * A common way to extract configuration from this interface
     * Designed to replace a plenty of existing methods
     * and prevent creating new ones.
     * <p>
     * Once the application gets
     * a new customizable item, this should be represented by adding
     * a new instance of {@link ConfigurationItem}.
     * Preferably, such information about a configuration item
     * like a property name, xpath for extraction, a URL
     * and/or other representation-specific points,
     * should be encapsulated in instances of {@link ConfigurationItem}.
     *
     * @param     configurationItem                a key for the parameter
     *                                             value you want
     *                                             to extract from this
     *                                             container
     * @return                                     a value
     *                                             stored by passed key
     *                                             in a {@link String}
     *                                             representation
     * @exception java.util.NoSuchElementException if this container
     *                                             does not have configured
     *                                             value for the passed
     *                                             {@code configurationItem}
     * @exception NullPointerException             if passed
     *                                             {@code configurationItem}
     *                                             is {@code null}
     * @see       ConfigurationItem
     * */
    String get(ConfigurationItem configurationItem);

    /**
     * Informs if this container has value for the {@code configurationItem}
     *
     * @param       configurationItem       a key of checked property
     * @return                              {@code true} if this container
     *                                      contains value for
     *                                      {@code configurationItem},
     *                                      {@code false} otherwise
     * @exception   NullPointerException    if passed {@code configurationItem}
     *                                      is {@code null}
     * */
    boolean contains(ConfigurationItem configurationItem);

    /**
     * Returns all the configuration items for which this container has values
     *
     * @return a {@link Set} of configured {@link ConfigurationItem}s
     * */
    Set<ConfigurationItem> items();
}
