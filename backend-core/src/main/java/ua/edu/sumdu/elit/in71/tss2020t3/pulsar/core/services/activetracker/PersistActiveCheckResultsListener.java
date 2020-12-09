package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult;

@AllArgsConstructor
public class PersistActiveCheckResultsListener implements JobListener {

    public static final String LISTENER_NAME = "ActiveTrackerListener";

    private static final Logger LOGGER = Logger.getLogger(
        PersistActiveCheckResultsListener.class
    );

    private final HttpAccessibilityService service;

    @Override
    public String getName() {
        return LISTENER_NAME;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        LOGGER.trace("Executing " + LISTENER_NAME + ". Context: " + context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        LOGGER.trace(LISTENER_NAME + " execution vetoed. Context: " + context);
    }

    @Override
    public void jobWasExecuted(
        JobExecutionContext context,
        JobExecutionException jobException
    ) {
        service.save((HttpAccessibilityCheckResult) context.getResult());
    }
}
