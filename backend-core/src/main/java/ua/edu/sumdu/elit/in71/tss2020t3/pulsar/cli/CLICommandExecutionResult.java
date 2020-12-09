package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli;

/**
 * Interface-container for a
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.CLICommand}
 * execution result
 * <p>
 * Implementations should be immutable.
 * That is, once an instance of {@link CLICommandExecutionResult} is returned,
 * its {@link #getResult()} method should always return the same object
 * or {@code null}.
 *
 * @param   <T> is a type of execution result
 * @see     ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.CLICommand
 * @author  Mykhailo Birintsev
 * */
public interface CLICommandExecutionResult<T> {

    /**
     * A Constant that indicates that something went wrong during
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.CLICommand}
     * execution
     *
     * @see picocli.CommandLine#execute(String...)
     * */
    int UNKNOWN_ERROR_EXECUTION_RESULT_CODE = -1;

    /**
     * A common way to extract the result
     * <p>
     * This method should always return the same object or {@code null}
     *
     * @return a command execution result
     * */
    T getResult();
}
