package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli;

import org.apache.log4j.Logger;
import picocli.CommandLine;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.CLICommand;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.StartApplicationInstanceCLICommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class represents an implementation of Front Controller pattern
 * for Command Line Interface (CLI)
 *
 * @see     CLICommand
 * @see     CLICommandExecutionResult
 * @author  Mykhailo Birintsev
 * */
public class CLIFrontController {

    private final Collection<CLICommand<?>> supportedCLICommands;

    private static final Logger LOGGER = Logger.getLogger(
        CLIFrontController.class
    );

    /**
     * A separator that splits program arguments into separate {@link String}s
     * in {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.Main#main(String[])}
     * */
    public static final String PROGRAM_ARGUMENTS_SEPARATOR = String.valueOf(
        ' '
    );

    /**
     * A default constructor
     * */
    public CLIFrontController() {
        supportedCLICommands = new ArrayList<>();
        supportedCLICommands.add(new StartApplicationInstanceCLICommand());
    }

    /**
     * This method recognizes command line input, parses it (if necessary)
     * and dispatches handling to required controller ({@link CLICommand})
     *
     * @param       args                            a set of command line input
     *                                              parameters
     * @exception   UnsupportedOperationException   if there is not any command
     *                                              that can handle
     *                                              provided command line input
     *
     * @see                                         CLICommand
     * */
    public void handle(String[] args) {
        for (CLICommand<?> command : supportedCLICommands) {
            if (isCLIInputForCommandInvocation(command, args)) {
                String[] commandArs = extractCommandArguments(command, args);
                command.setArgs(commandArs);
                try {
                    new CommandLine(command).setParameterExceptionHandler(
                        (parameterException, passedArgs) -> {
                            LOGGER.error(parameterException);
                            return
                                CLICommandExecutionResult
                                    .UNKNOWN_ERROR_EXECUTION_RESULT_CODE;
                        }
                    ).execute(commandArs);
                    return;
                } catch (Exception e) {
                    LOGGER.error(
                        "Error during the command execution:"
                            + System.lineSeparator() + "args: "
                            + Arrays.toString(args) + System.lineSeparator()
                            + "Command: " + command
                        , e
                    );
                    throw new RuntimeException(e);
                }
            }
        }
        throw new UnsupportedOperationException(
            "Can not find command line handler for the input below:"
                + System.lineSeparator() + Arrays.toString(args)
        );
    }

    /**
     * Extracts command options and position parameters from user input
     * <p>
     * This method separates the whole user input from the console into 2 parts:
     * <ol>
     * <li>Command name</li>
     * <li>Command options and positional parameters</li>
     * </ol>
     *
     * @param       cliInput                    program arguments
     * @param       command                     a command
     * @return                                  a "command options and
     *                                          positional parameters" portion
     *                                          of user input.
     *                                          If only command name passed,
     *                                          empty array is returned.
     * @exception   IllegalArgumentException    if passed {@code cliInput}
     *                                          is not aimed to invoke
     *                                          the {@code command}
     * @see                                     CommandLine.Command#name()
     * @see                                     #isCLIInputForCommandInvocation
     * */
    private static String[] extractCommandArguments(
        CLICommand<?> command, String[] cliInput
    ) {
        String commandLineInput = String.join(
            PROGRAM_ARGUMENTS_SEPARATOR, cliInput
        );
        String commandName = new CommandLine(command).getCommandName();
        if (!isCLIInputForCommandInvocation(command, cliInput)) {
            throw new IllegalArgumentException(
                "Passed input is not aimed to invoke the command: "
                    + commandName + System.lineSeparator()
                    + "Input:" + System.lineSeparator() + commandLineInput
            );
        }
        return commandLineInput
            .substring(
                commandLineInput.indexOf(commandName) + commandName.length()
            )
            .trim()
            .split(PROGRAM_ARGUMENTS_SEPARATOR);
    }

    /**
     * This method detects whether {@code cliInput} is an invocation
     * of {@code command}.
     * <p>
     * <strong>Note</strong>, that this method does not perform any validation.
     * That is, main target of this method is comparison whether or not
     * passed command line input starts with the command name.
     *
     * @param   command     a command object
     * @param   cliInput    a user command line input (program arguments)
     * @return              {@code true} if passed {@code cliInput} is aimed
     *                      to invoke passed command, {@code false} otherwise
     * @see                 picocli.CommandLine.Command
     * @see                 CommandLine
     * */
    private static boolean isCLIInputForCommandInvocation(
        CLICommand<?> command, String[] cliInput
    ) {
        return String.join(PROGRAM_ARGUMENTS_SEPARATOR, cliInput)
            .startsWith(new CommandLine(command).getCommandName());
    }
}
