package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands;

import org.apache.log4j.Logger;
import picocli.CommandLine;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.CLICommandExecutionResult;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.*;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.ApplicationConfigurationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.config.PropertiesAppConfigService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class is a common way to start an application instance from command line
 *
 *	<p>
 *		It is supposed, that an application instance will be started as following
 *		{@code java -jar /path/to/jar startup parameters}
 *	</p>
 *
 *	<p>A list of {@code startup parameters} is below:</p>
 *     <ul>
 *          <li>{@code -f, -file} - a configuration file path (see {@link ApplicationConfiguration})</li>
 *     </ul>
 *
 *
 * @see			CLICommand
 * @see			ApplicationConfiguration
 * */
@CommandLine.Command(name = "start", description = "Command for starting application instance")
public class StartApplicationInstanceCLICommand implements CLICommand<Application> {

	@CommandLine.Option(names = {"-f", "-file"})
	private File customApplicationPropertiesFile;

	private final ApplicationConfigurationService appConfigService;

	private static final Logger LOGGER = Logger.getLogger(StartApplicationInstanceCLICommand.class);

	public StartApplicationInstanceCLICommand() {
		appConfigService = new PropertiesAppConfigService();
	}

	/**
	 *	Starts an application instance according to passed {@code args}.
	 *
	 *	See arguments description on the class-level javadoc
	 *
	 * @param			args	A set of command line parameters for application instance startup. See example of valid 
	 *                          {@code args} in javadoc for {@link StartApplicationInstanceCLICommand#canHandle}
	 *
	 * @return			An instance of running application instance
	 *
	 * @throws			Exception If some error occurred during application starting
	 *
	 * @exception		FileNotFoundException If specified config file was not found
	 *
	 * @see				Application
	 * */
	@Override
	public CLICommandExecutionResult<Application> execute(String[] args) throws Exception {
		if (!canHandle(args)) {
			throw new IllegalArgumentException(
				"This command can not handle provided command line input:" +
				System.lineSeparator() + Arrays.toString(args)
			);
		}
		ApplicationConfiguration appConfig;
		if (customApplicationPropertiesFile != null) {
			if (customApplicationPropertiesFile.isFile()) {
				appConfig = appConfigService.parse(
					new FileInputStream(customApplicationPropertiesFile),
					getClass().getResourceAsStream("/application.properties")
				);
			} else {
				throw new FileNotFoundException(
					"Can not find server configurations file " + customApplicationPropertiesFile.getAbsolutePath()
				);
			}
		} else {
			appConfig = appConfigService.parse(
				getClass().getResourceAsStream("/application.properties")
			);
		}
		Application application = new JavalinApplication(appConfig);
		LOGGER.info("Application instantiated, startup config below" + System.lineSeparator() + appConfig);
		application.start();
		return () -> application;
	}

	/**
	 *	Informs whether passed arguments relates to this command.
	 *
	 *	<p>
	 *			It is assumed that <strong>supposedly</strong> valid set of startup {@code args}
	 *		has "start" keyword as its first element. Note, that the keyword is case-sensitive.
	 *	</p>
	 *
	 *	<p>Examples of valid sets of startup arguments are below:</p>
	 *     <ul>
	 *         <li>{@code start -f /path/to/application/config/file}</li>
	 *         <li>{@code start -file /path/to/application/config/file}</li>
	 *     </ul>
	 *
	 *
	 * */
	@Override
	public boolean canHandle(String[] args) {
		return args != null && args.length != 0 && Arrays.stream(args).noneMatch(Objects::isNull) &&
			"start".equals(args[0]);
	}
}
