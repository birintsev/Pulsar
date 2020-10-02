package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI;

import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.CLICommand;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.StartApplicationInstanceCLICommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class represents an implementation of Front Controller pattern for Command Line Interface (CLI)
 * */
public class CLIFrontController {

	private final Collection<CLICommand<?>> supportedCLICommands;

	private static final Logger LOGGER = Logger.getLogger(CLIFrontController.class);

	public CLIFrontController() {
		supportedCLICommands = new ArrayList<>();
		supportedCLICommands.add(new StartApplicationInstanceCLICommand());
	}

	/**
	 * This method recognizes command line input, parses it (if necessary)
	 * and dispatches handling to required controller ({@link CLICommand})
	 *
	 * @param			args	A set of command line input parameters
	 *
	 * @exception		UnsupportedOperationException	In case if there is not any command
	 *					that can handle provided command line input
	 *
	 * @see				CLICommand
	 * */
	public void handle(String [] args) {
		for (CLICommand<?> command : supportedCLICommands) {
			if (command.canHandle(args)) {
				try {
					command.execute(args);
					return;
				} catch (Exception e) {
					LOGGER.error(
						"Error during the command execution:" + System.lineSeparator()
							+ "args: " + Arrays.toString(args) + System.lineSeparator() + "Command: " + command
						, e
					);
					throw new RuntimeException(e);
				}
			}
		}
		throw new UnsupportedOperationException(
			"Can not find command line handler for the input below:" + System.lineSeparator() + Arrays.toString(args)
		);
	}
}
