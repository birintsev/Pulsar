package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI;

/**
 * Interface-container for a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands.CLICommand} execution result
 * */
public interface CLICommandExecutionResult<T> {
	T getResult();
}
