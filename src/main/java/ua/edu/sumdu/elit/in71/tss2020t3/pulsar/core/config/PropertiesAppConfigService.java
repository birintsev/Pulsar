package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class provides a default implementation
 * for {@link ApplicationConfigurationService}
 * working with {@code .property} files
 *
 * @author Mykhailo Birintsev
 * @deprecated see details on package-level documentation
 * */
public class PropertiesAppConfigService
    implements ApplicationConfigurationService {

    /**
     * Parses {@code .property} formatted application configuration
     *
     * @return  an instance of {@link PropertyBasedAppConfig}
     *          that represents parsed properties
     * @see     Properties
     * @see     PropertyBasedAppConfig
     * */
    @Override
    public ApplicationConfiguration parse(InputStream is) throws IOException {
        return new PropertyBasedAppConfig(parseProperties(is));
    }

    /**
     * Serves as an aggregation method for two bunches of properties
     * overridden one by another
     *
     * @param defaultConfig a source for default property values
     * @param customConfig  a source for custom property values
     * */
    @Override
    public ApplicationConfiguration parse(
        InputStream customConfig, InputStream defaultConfig
    ) throws IOException {
        Properties effectiveProperties = new Properties(
            parseProperties(defaultConfig)
        );
        Properties customProperties = parseProperties(customConfig);
        for (String key : customProperties.stringPropertyNames()) {
            effectiveProperties.setProperty(
                key, customProperties.getProperty(key)
            );
        }
        return new PropertyBasedAppConfig(effectiveProperties);
    }

    private Properties parseProperties(InputStream is) throws IOException {
        Properties properties = new Properties();
        properties.load(is);
        return properties;
    }
}
