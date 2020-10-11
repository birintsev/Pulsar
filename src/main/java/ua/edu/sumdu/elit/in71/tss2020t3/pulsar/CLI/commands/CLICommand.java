package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.commands;

import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.CLI.CLICommandExecutionResult;

/**
 * This is an interface-decorator, that is designed mostly for classes
 * marked by {@link picocli.CommandLine.Command}
 * to add them additional properties.
 *
 * @param   <T> A type of command execution result.
 *              That is, a command could produce an object of some type,
 *              {@code null} or just be of a {@link Void} result type.
 * @see         StartApplicationInstanceCLICommand
 * @see         CLICommandExecutionResult
 * @author      Mykhailo Birintsev
 * */
public interface CLICommand<T> {

    /**
     * This method is used to recognize if an instance of {@link CLICommand}
     * can handle passed set of parameters.
     * Note, that it does not guarantees validity of input.
     * Validation logic should be implemented additionally.
     * <p>
     * It is supposed that {@code args} will be passed from the command line,
     * however it is not required.
     *
     * @param   args    a set of command line parameters.
     * @return          {@code true} if this object can execute provided
     *                  command line input.
     * */
    boolean canHandle(String[] args);

    /**
     * This is a common method to start command execution.
     * <p>
     * Implementations of this method should not return {@code null} values.
     * If it's really necessary to do so,
     * an instance of {@link CLICommandExecutionResult} should wrap it.
     *
     * @param   args        A set of command line parameters.
     * @return              Command execution result container.
     * @throws  Exception   In case of any error.
     *                      Preferably, should be over-declared
     *                      in implementations with more specific
     *                      {@link Exception} inheritors.
     * */
    CLICommandExecutionResult<T> execute(String[] args) throws Exception;

    /**
     * A method that provides a way
     * to preset command arguments without execution
     *
     * @param args  A set of command line parameters.
     *
     * @return      {@code this} object
     * */
    CLICommand<T> setArgs(String[] args);

    /**
     * This method is an alternative way to execute a command
     *
     * @return  Command execution result container
     * @see     CLICommand#execute(String[] args)
     * */
    CLICommandExecutionResult<T> execute() throws Exception;
}
