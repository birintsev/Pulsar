package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import java.io.IOException;
import java.util.Properties;
import org.quartz.Job;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.ActiveTrackerJob;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.PersistActiveCheckResultsListener;

/**
 * A Guice configuration module for Active Tracker related modules
 * (mostly - {@link org.quartz})
 * */
public class ActiveTrackerModule extends AbstractModule {

    @Provides
    @Named("ActiveTrackerJobClass")
    Class<? extends Job> activeTrackerJobClass() {
        return ActiveTrackerJob.class;
    }

    @Provides
    @Named("PersistActiveCheckResultsListener")
    JobListener persistActiveCheckResultsListener(
        HttpAccessibilityService accessibilityService
    ) {
        return new PersistActiveCheckResultsListener(accessibilityService);
    }

    @Provides
    Scheduler scheduler(
        @Named("PersistActiveCheckResultsListener")
            JobListener persistActiveCheckResultsListener
    ) throws SchedulerException, IOException {
        Scheduler scheduler =
            new StdSchedulerFactory(getQuartzProperties())
                .getScheduler();
        scheduler
            .getListenerManager()
            .addJobListener(
                persistActiveCheckResultsListener
            );
        return scheduler;
    }

    private Properties getQuartzProperties() {
        Properties quartzProperties = new Properties();
        String quartzPropertiesPrefix = "org.quartz."; // todo map pulsar.quartz
        System.getProperties().stringPropertyNames()   // todo to org.quartz
            .stream()                                  // todo properties
            .filter(
                name -> name.startsWith(quartzPropertiesPrefix)
            )
            .forEach(
                name -> quartzProperties
                    .setProperty(
                        name,
                        System.getProperty(name)
                    )
            );
        return quartzProperties;
    }
}
