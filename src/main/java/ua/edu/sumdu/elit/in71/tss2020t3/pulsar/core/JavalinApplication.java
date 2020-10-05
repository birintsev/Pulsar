package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import io.javalin.Javalin;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem;

/**
 * A default {@link Application} implementation based on <a href="https://javalin.io/">Javalin</a> framework
 *
 * @see			Application
 * @see			ApplicationConfiguration
 * */
public class JavalinApplication implements Application {

	private final Javalin app;

	private final ApplicationConfiguration appConfig;

	private final SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(JavalinApplication.class);

	/**
	 * @param			appConfig	A set of effective application properties that will be used during startup
	 *								and in runtime
	 * */
	public JavalinApplication(ApplicationConfiguration appConfig) {
		this.appConfig = appConfig;
		sessionFactory = createSessionFactory(appConfig);
		app = createApp(appConfig, sessionFactory);
	}

	@Override
	public void start() {
		app.start(Integer.parseInt(appConfig.get(ConfigurationItem.SERVER_PORT)));
	}

	private Javalin createApp(ApplicationConfiguration appConfig, SessionFactory sessionFactory) {
		Javalin javalin = Javalin.create().post("/api/endpoint", new NewClientHostStatisticHandler(sessionFactory));
		return javalin;
	}

	private SessionFactory createSessionFactory(ApplicationConfiguration appConfig) {
		Configuration hibernateConfig = new Configuration();
		hibernateConfig.configure()
			.setProperty("hibernate.connection.driver_class", appConfig.get(ConfigurationItem.DATABASE_DRIVER_CLASS))
			.setProperty("hibernate.dialect", appConfig.get(ConfigurationItem.DATABASE_DIALECT))
			.setProperty("hibernate.connection.password", appConfig.get(ConfigurationItem.DATABASE_PASSWORD))
			.setProperty("hibernate.connection.username", appConfig.get(ConfigurationItem.DATABASE_USER))
			.setProperty("hibernate.connection.url", appConfig.get(ConfigurationItem.DATABASE_URL));
		return hibernateConfig.buildSessionFactory();
	}
}
