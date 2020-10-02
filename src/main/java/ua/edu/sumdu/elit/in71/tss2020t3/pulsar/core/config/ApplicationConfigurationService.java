package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.io.IOException;
import java.io.InputStream;

public interface ApplicationConfigurationService {

	/**
	 * This method is expected to be a common way of creating {@link ApplicationConfiguration}.
	 *
	 * @param		is	A source for application configuration (from which it will completely/partially be read).
	 *
	 * @return		An instance of {@link ApplicationConfiguration} read from passed source.
	 *
	 * @throws		IOException		In case of any I/O exception during reading from streams.
	 *
	 * @see			ApplicationConfigurationService#parse
	 * @see			ApplicationConfiguration
	 * */
	ApplicationConfiguration parse(InputStream is) throws IOException;
	/**
	 *	This method should be invoked when it is necessary to get effective {@link ApplicationConfiguration} instance
	 * which is expected to be used for an application instance startup and runtime.
	 *
	 * @param			customConfig	A stream from which custom config can be read.
	 * @param			defaultConfig	A stream from which default config can be read.
	 *
	 * @return			An instance of {@link ApplicationConfiguration} which has default configuration overridden by
	 * 					custom configuration
	 *
	 * @throws			IOException		In case of any I/O exception during reading from streams.
	 * */
	ApplicationConfiguration parse(InputStream customConfig, InputStream defaultConfig)
		throws IOException;
}
