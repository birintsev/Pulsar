package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.io.IOException;
import java.io.InputStream;

/**
 * A service that provides common methods to work with
 * {@link ApplicationConfiguration}
 *
 * @author Mykhailo Birintsev
 * */
public interface ApplicationConfigurationService {

    /**
     * This method should be a common way of creating
     * {@link ApplicationConfiguration}
     *
     * @param   is          a source for application configuration
     * @return              an instance of {@link ApplicationConfiguration}
     *                      read from passed source
     * @throws  IOException if any I/O exception occurs
     *                      during reading from the stream
     * @see                 ApplicationConfiguration
     * */
    ApplicationConfiguration parse(InputStream is) throws IOException;

    /**
     * This method should be invoked when it is necessary to get effective
     * {@link ApplicationConfiguration} instance which is expected to be used
     * for an application instance startup and runtime
     *
     * @param   customConfig    a stream from which
     *                          custom config can be read
     * @param   defaultConfig    a stream from which
     *                          default config can be read
     * @return                  an instance of {@link ApplicationConfiguration}
     *                          which has default configuration
     *                          overridden by custom configuration
     *
     * @throws  IOException     if any I/O exception occurs
     *                          during reading from the stream
     * @see                     ApplicationConfiguration
     * */
    ApplicationConfiguration parse(
        InputStream customConfig, InputStream defaultConfig
    ) throws IOException;
}
