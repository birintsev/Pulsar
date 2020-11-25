package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.javalin.Javalin;
import java.util.HashSet;
import java.util.function.Supplier;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.CLIFrontController;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.CLICommand;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli.commands.StartApplicationInstanceCLICommand;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.Application;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.JavalinApplication;

/**
 * A Guice injection configuration class for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.cli} classes
 * dependencies configuration
 * */
public class CLIModule extends AbstractModule {

    @Provides
    StartApplicationInstanceCLICommand startCommand(
        Supplier<Application> applicationProvider
    ) {
        return new StartApplicationInstanceCLICommand(applicationProvider);
    }

    @Provides
    CLIFrontController cliFrontController(
        StartApplicationInstanceCLICommand startCommand
    ) {
        HashSet<CLICommand<?>> supportedCommands = new HashSet<>();
        supportedCommands.add(startCommand);
        return new CLIFrontController(supportedCommands);
    }

    @Provides
    Supplier<Application> javalinApplicationProvider(Injector injector) {
        return () -> new JavalinApplication(
            injector.getInstance(Javalin.class)
        );
    }
}
