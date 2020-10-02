package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import io.javalin.Javalin;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfiguration;

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
		app.start(appConfig.getPort());
	}

	private Javalin createApp(ApplicationConfiguration appConfig, SessionFactory sessionFactory) {
		Javalin javalin = Javalin.create().post("/api/endpoint", new NewClientHostStatisticHandler(sessionFactory));
		return javalin;
	}

	private SessionFactory createSessionFactory(ApplicationConfiguration appConfig) {
		Configuration hibernateConfig = new Configuration();
		hibernateConfig.configure()
			.setProperty("hibernate.connection.driver_class", appConfig.getDatabaseConnectionDriver())
			.setProperty("hibernate.dialect", appConfig.getDatabaseConnectionDriver())
			.setProperty("hibernate.connection.password", appConfig.getDatabasePassword())
			.setProperty("hibernate.connection.username", appConfig.getDatabaseUser())
			.setProperty("hibernate.connection.url", appConfig.getDatabaseConnectionURL())
			.setProperty("hibernate.dialect", appConfig.getDialect());
		return hibernateConfig.buildSessionFactory();
	}
}
