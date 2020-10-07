package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI;

import picocli.CommandLine;

/**
 * Interface-container for a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.CLICommand} execution result
 * */
public interface CLICommandExecutionResult<T> {

	/**
	 * Constant that indicates that something went wrong during
	 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.CLICommand} execution
	 *
	 * @see            CommandLine#execute(String...)
	 * */
	int UNKNOWN_ERROR_EXECUTION_RESULT_CODE = -1;

	/**
	 * @return			a command execution result
	 * */
	T getResult();
}
