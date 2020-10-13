package ua.edu.sumdu.elit.in71.tss2020t3.pulsar;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.CLIFrontController;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.StartApplicationInstanceCLICommand;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem;

public class Main {

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Main.class);
        try {
            System.setProperty(
                ConfigurationItem.LOG_DIRECTORY.getPropertyName(),
                getRunningDirectory().getAbsolutePath()
            );
            reconfigureLoggers();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new CLIFrontController().handle(args);
    }

    /**
     * @return  a {@link File} instance that represents the parent directory
     *          of this jar file
     * */
    public static File getRunningDirectory() throws Exception {
        return new File(
            new File(StartApplicationInstanceCLICommand.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParent()
        );
    }

    /**
     * Reloads log4j configuration
     * */
    public static void reconfigureLoggers() {
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(
            Main.class.getResourceAsStream("/log4j.properties")
        );
    }
}
