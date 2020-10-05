package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

/**
 * <p>This interface was designed to be a container of server configuration
 * which (probably) would be provided/represented in different ways
 * (e.g. in <i>.properties</i> file, <i>.xml</i> file, on the local drive or via network)</p>
 *
 * <p>Note that <b>this class does not provide any information on configuration validity</b>.
 * That is, it is no more than just an abstraction, wrapper</p>
 * */
public interface ApplicationConfiguration {

	/**
	 * @return			An integer between 0 and 65535, representing port number that application will use.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	int getPort();

	/**
	 * @return			A database connection URL set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.url</i> key.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	String getDatabaseConnectionURL();

	/**
	 * @return			A database username set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.user</i> key.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	String getDatabaseUser();

	/**
	 * @return			A database user profile password set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.password</i> key.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	String getDatabasePassword();

	/**
	 * @return			A driver class name set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.driver_class</i> key.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	String getDatabaseConnectionDriver();

	/**
	 * @return			A dialect class name set in configuration.
	 * 					For properties-based configuration is a value of <i>database.dialect</i> key.
	 *
	 * @deprecated		use {@link ApplicationConfiguration#get(ConfigurationItem)} instead.
	 * */
	@Deprecated
	String getDialect();

	/**
	 * Designed to replace a plenty of existing methods and prevent creating new ones. Once the application gets
	 * a customizable item, it should be represented by an instance of {@link ConfigurationItem}
	 *
	 * @see			ConfigurationItem
	 * */
	String get(ConfigurationItem configurationItem);
}
