package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands;

import picocli.CommandLine;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.CLICommandExecutionResult;

/**
 * Interface-decorator, is designed mostly for classes marked by {@link CommandLine.Command}s
 * to add them additional properties.
 *
 * @param		<T>  A type of command execution result. That is, a command could produce an object of some type
 *                 or {@code null} or just be of a {@link Void} result type.
 *
 * @see			StartApplicationInstanceCLICommand
 * @see			CLICommandExecutionResult
 * */
public interface CLICommand<T> {

	/**
	 * This method is used to recognize if instance of {@link CLICommand} can handle passed set of parameters.
	 * Note, that it does not guarantees validity of input. Validation logic should be implemented additionally.
	 *
	 * It is supposed that {@code args} will be passed from the command line, however it is not required.
	 *
	 * @param			args	A set of command line parameters.
	 *
	 * @return			{@code true} if this object can execute provided command line input.
	 * */
	boolean canHandle(String [] args);

	/**
	 * Common method to start command execution.
	 *
	 * Implementations of this method should not return {@code null} values. If it's really necessary to do so,
	 * an instance of {@link CLICommandExecutionResult} should wrap it.
	 *
	 * @param			args	A set of command line parameters.
	 *
	 * @return			Command execution result container.
	 *
	 * @throws			Exception in case of any error. Preferably, should be over-declared in implementations
	 * 					with more specific {@link Exception} inheritors.
	 * */
	CLICommandExecutionResult<T> execute(String [] args) throws Exception;
}
