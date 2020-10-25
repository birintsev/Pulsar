package ua.edu.sumdu.elit.in71.tss2020t3.pulsar;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.CLIFrontController;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.StartApplicationInstanceCLICommand;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem.LOG_DIRECTORY;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ConfigurationItem.RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private static final String JAVA_PROTOCOL_HANDLERS_PKGS_PROPERTY =
        "java.protocol.handler.pkgs";

    static {
        System.setProperty(
            LOG_DIRECTORY.getPropertyName(),
            LOG_DIRECTORY.getDefaultValue()
        );
        reconfigureLoggers();
        System.setProperty(
            RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI.getPropertyName(),
            RESPONSE_ON_UNKNOWN_ERROR_RESOURCE_URI.getDefaultValue()
        );

        String newProtocolHandlersPackage =
            "ua.edu.sumdu.elit.in71.tss2020t3.pulsar.protocolhandlers";
        String oldProtocolHandlersPackages = System.getProperty(
            JAVA_PROTOCOL_HANDLERS_PKGS_PROPERTY
        );
        System.setProperty(
            JAVA_PROTOCOL_HANDLERS_PKGS_PROPERTY,
            oldProtocolHandlersPackages == null
                ? newProtocolHandlersPackage
                : newProtocolHandlersPackage + "|" + oldProtocolHandlersPackages
        );
    }

    public static void main(String[] args) {
        new CLIFrontController().handle(args);
    }

    /**
     * @return                          a {@link File} instance that represents
     *                                  the parent directory of this jar file
     * @exception   RuntimeException    if an exception occurs
     *                                  when identifying parent folder
     *                                  for a jar in runtime
     * */
    public static File getRunningDirectory() {
        try {
            return new File(
                new File(StartApplicationInstanceCLICommand.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()).getParent()
            );
        } catch (Exception e) {
            LOGGER.error(
                "Can not identify parent folder for the running jar file", e
            );
            throw new RuntimeException(e);
        }
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
