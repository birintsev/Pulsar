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
	 * */
	int getPort();

	/**
	 * @return			A database connection URL set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.url</i> key.
	 * */
	String getDatabaseConnectionURL();

	/**
	 * @return			A database username set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.user</i> key.
	 * */
	String getDatabaseUser();

	/**
	 * @return			A database user profile password set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.password</i> key.
	 * */
	String getDatabasePassword();

	/**
	 * @return			A driver class name set in configuration.
	 * 					For properties-based configuration is a value of <i>database.connection.driver_class</i> key.
	 * */
	String getDatabaseConnectionDriver();

	/**
	 * @return			A dialect class name set in configuration.
	 * 					For properties-based configuration is a value of <i>database.dialect</i> key.
	 * */
	String getDialect();
}
