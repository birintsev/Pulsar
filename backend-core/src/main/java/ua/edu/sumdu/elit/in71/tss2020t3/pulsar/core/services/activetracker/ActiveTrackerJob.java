package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessQrtzService.CONFIGURATION_JOB_CONTEXT_KEY;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult;

public class ActiveTrackerJob implements Job {

    private static final Logger LOGGER = Logger.getLogger(
        ActiveTrackerJob.class
    );

    private final
    Function<JobExecutionContext, HttpAccessibilityCheckConfiguration>
        configurationExtractor =
        context ->
            (HttpAccessibilityCheckConfiguration) context
                .getMergedJobDataMap()
                .get(CONFIGURATION_JOB_CONTEXT_KEY);

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        HttpAccessibilityCheckConfiguration configuration =
            configurationExtractor.apply(context);
        LOGGER.trace(
            "[START] Check for "
                + configuration
        );
        context.setResult(
            doCheck(
                configuration
            )
        );
        LOGGER.trace(
            "[END] Check for "
                + configuration
        );
    }

    private HttpAccessibilityCheckResult doCheck(
        HttpAccessibilityCheckConfiguration configuration
    ) {
        URL targetUrl = configuration.getTargetUrl();
        Duration responseTimeout = configuration.getResponseTimeout();
        HttpAccessibilityCheckResult checkResult =
            new HttpAccessibilityCheckResult(
                configuration,
                null,
                null,
                null,
                null
            );
        Integer responseCode = null;
        Duration responseTime = null;
        long timeBeforeConnection;
        try {
            HttpURLConnection httpConnection;
            httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(
                (int) responseTimeout.toMillis()
            );
            timeBeforeConnection = System.currentTimeMillis();
            httpConnection.connect();
            responseTime = Duration.of(
                System.currentTimeMillis() - timeBeforeConnection,
                ChronoUnit.MILLIS
            );
            responseCode = httpConnection.getResponseCode();
            httpConnection.disconnect();
        } catch (SocketTimeoutException e) {
            LOGGER.error(e);
            checkResult.setDescription(
                "Timed out"
            );
        } catch (IOException e) {
            LOGGER.error(e);
            checkResult.setDescription(
                e.getMessage()
            );
        } finally {
            checkResult.setResponseTime(responseTime);
            checkResult.setResponseCode(responseCode);
            checkResult.setCheckedWhen(ZonedDateTime.now());
        }
        return checkResult;
    }

}
