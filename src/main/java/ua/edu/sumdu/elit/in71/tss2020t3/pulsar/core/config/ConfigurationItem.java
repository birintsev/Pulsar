package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

/**
 * This is an enum that represents a configurable item for an application instance.
 * */
public enum ConfigurationItem {

	SERVER_PORT("pulsar.server.port"),
	DATABASE_URL("pulsar.database.connection.url"),
	DATABASE_USER("pulsar.database.connection.user"),
	DATABASE_PASSWORD("pulsar.database.connection.password"),
	DATABASE_DRIVER_CLASS("pulsar.database.connection.driver_class"),
	DATABASE_DIALECT("pulsar.database.dialect"),
	LOG_DIRECTORY("pulsar.log.directory");

	/**
	 * A name of this configuration item, configured in a {@code .properties} file
	 *
	 * @see			java.util.Properties
	 * @see			PropertyBasedAppConfig
	 * @see			PropertiesAppConfigService
	 * */
	private final String propertyName;

	ConfigurationItem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * This method is created to support {@code .property}-based configuration of an application instance
	 *
	 * @return			a name of a property
	 * */
	public String getPropertyName() {
		return propertyName;
	}
}
