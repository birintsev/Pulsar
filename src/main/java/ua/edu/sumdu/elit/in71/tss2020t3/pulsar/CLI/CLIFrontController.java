package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI;

import org.apache.log4j.Logger;
import picocli.CommandLine;
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

	public static final String PROGRAM_ARGUMENTS_SEPARATOR = " ";

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
	public void handle(String[] args) {
		for (CLICommand<?> command : supportedCLICommands) {
			if (isCLIInputForCommandInvocation(command, args)) {
				String[] commandArs = extractCommandArguments(command, args);
				command.setArgs(commandArs);
				try {
					new CommandLine(command).setParameterExceptionHandler((ex, args1) -> {
						LOGGER.error(ex);
						return CLICommandExecutionResult.UNKNOWN_ERROR_EXECUTION_RESULT_CODE;
					}).execute(commandArs);
					return;
				} catch (Exception e) {
					LOGGER.error(
						"Error during the command execution:" + System.lineSeparator() + "args: "
							+ Arrays.toString(args) + System.lineSeparator() + "Command: " + command
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

	/**
	 * This method separates the whole user input from the console into 2 parts:
	 * <ol>
	 *     <li>Command name</li>
	 *     <li>Command options and positional parameters</li>
	 * </ol>
	 *
	 * <p><strong>Note</strong>, that a command name must not be blank</p>
	 * <p>First element of passed array is treat as command name</p>
	 *
	 * @param			CLIInput 	Is a part of command line user input called "program arguments"
	 * @param			command		Is a command
	 *
	 * @return			A "command options and positional parameters" portion of user input.
	 * 					If only command name passed, empty array is returned.
	 *
	 * @exception		IllegalArgumentException If passed {@code CLIInput} is not aimed to invoke the {@code command}
	 *
	 * @see				CommandLine.Command#name()
	 * @see				#isCLIInputForCommandInvocation(CLICommand, String[])
	 * */
	private static String[] extractCommandArguments(CLICommand<?> command, String[] CLIInput) {
		String commandLineInput = String.join(PROGRAM_ARGUMENTS_SEPARATOR, CLIInput);
		String commandName = new CommandLine(command).getCommandName();
		if (!isCLIInputForCommandInvocation(command, CLIInput)) {
			throw new IllegalArgumentException(
				"Passed input is not aimed to invoke the command: " + commandName + System.lineSeparator() +
					"Input:" + System.lineSeparator() + commandLineInput
			);
		}
		return commandLineInput
			.substring(commandLineInput.lastIndexOf(commandName) + commandName.length()).trim()
			.split(PROGRAM_ARGUMENTS_SEPARATOR);
	}

	/**
	 * This method detects whether {@code CLIInput} is an invocation of {@code command}.
	 * <strong>Note</strong>, that this method does not perform any validation. That is, main target of this method is
	 * comparison whether or not passed command line input starts with the command name.
	 *
	 * @param			command 	A command object
	 * @param			CLIInput	A user command line input (program arguments)
	 *
	 * @return			{@code true} if passed {@code CLIInput} is aimed to invoke passed command,
	 * 					{@code false} otherwise
	 *
	 * @see				picocli.CommandLine.Command
	 * @see				CommandLine
	 * */
	private static boolean isCLIInputForCommandInvocation(CLICommand<?> command, String[] CLIInput) {
		return String.join(PROGRAM_ARGUMENTS_SEPARATOR, CLIInput).startsWith(new CommandLine(command).getCommandName());
	}
}
