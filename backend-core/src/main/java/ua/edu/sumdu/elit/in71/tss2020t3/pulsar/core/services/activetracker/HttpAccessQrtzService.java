package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker;

import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.UserCheckConfigurationSubscription;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.NotExistsException;

/**
 * This service provides a default implementation of
 * {@link HttpAccessibilityService}
 * */
@AllArgsConstructor
public class HttpAccessQrtzService
implements HttpAccessibilityService {

    static final String CONFIGURATION_JOB_CONTEXT_KEY =
        HttpAccessibilityCheckConfiguration.class.getName();

    private static final Logger LOGGER = Logger.getLogger(
        HttpAccessibilityService.class
    );

    private static final String QUARTZ_ENTITIES_PREFIX = "PU_AT_";

    private static final Function<HttpAccessibilityCheckConfiguration, String>
        QUARTZ_JOB_NAMING_STRATEGY =
            configuration ->
                QUARTZ_ENTITIES_PREFIX
                    + configuration.getTargetUrl().toString();

    private static final Function<JobDetail, String>
        QUARTZ_TRIGGER_NAMING_STRATEGY =
            jobDetail ->
                QUARTZ_ENTITIES_PREFIX
                    + "TRIGGER_FOR_"
                    + jobDetail.getKey().getName();

    private static final String ACTIVE_TRACKER_JOBS_GROUP_NAME =
        QUARTZ_ENTITIES_PREFIX + "JOBS_GROUP";

    private static final String ACTIVE_TRACKER_TRIGGERS_GROUP_NAME =
        QUARTZ_ENTITIES_PREFIX + "TRIGGERS_GROUP";

    private final SessionFactory sessionFactory;

    private final Scheduler scheduler;

    private final Class<? extends Job> activeTrackerJobClass;

    @Override
    public HttpAccessibilityCheckConfiguration create(
        URL targetUrl,
        Duration interval,
        Duration responseTimeout
    ) throws AlreadyExistsException {
        HttpAccessibilityCheckConfiguration configuration = findByTargetUrl(
            targetUrl
        );
        if (configuration != null) {
            throw new AlreadyExistsException(
                "A configuration for "
                    + targetUrl
                    + " already exist: "
                    + configuration
            );
        }
        if (!isHttpOrHttpsProtocol(targetUrl)) {
            throw new IllegalArgumentException(
                "targetUrl is expected to be of HTTP or HTTPS protocol "
                    + "(found " + targetUrl.getProtocol() + ")"
            );
        }
        configuration = new HttpAccessibilityCheckConfiguration(
            targetUrl,
            interval,
            responseTimeout
        );

        // scheduling Quartz job
        // [1] the order is important!
        try {
            JobDetail jobDetail = createQuartzJobDetailsFor(configuration);
            scheduleQrtzJob(
                jobDetail,
                createTriggerFor(configuration, jobDetail)
            );
        } catch (SchedulerException e) {
            throw new RuntimeException("Can not schedule Quartz job", e);
        }

        // saving the configuration after successful scheduling Quartz job
        // [2] the order is important!
        save(configuration);

        return configuration;
    }

    @Override
    public HttpAccessibilityCheckConfiguration findByTargetUrl(URL targetUrl) {
        try (Session session = sessionFactory.openSession()) {
            List<HttpAccessibilityCheckConfiguration> configs =
                (List<HttpAccessibilityCheckConfiguration>) session
                    .createQuery(
                        "from HttpAccessibilityCheckConfiguration c "
                            + "where c.targetUrl = :targetUrl"
                    )
                    .setParameter("targetUrl", targetUrl)
                    .list();
            return configs.size() == 0 ? null : configs.get(0);
        }
    }

    @Override
    public void save(HttpAccessibilityCheckResult checkResult) {
        save((Object) checkResult);
    }

    @Override
    public HttpAccessibilityCheckConfiguration createIfNotExist(
        URL targetUrl,
        Duration responseTimeout,
        Duration interval
    ) {
        HttpAccessibilityCheckConfiguration configuration = findByTargetUrl(
            targetUrl
        );
        try {
            return configuration == null
                ? create(targetUrl, interval, responseTimeout)
                : configuration;
        } catch (AlreadyExistsException e) {
            LOGGER.error(
                "A configuration for performing active checks of "
                    + targetUrl
                    + " accessibility appeared unexpectedly",
                e
            );
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe(
        User user,
        HttpAccessibilityCheckConfiguration configuration,
        ZonedDateTime tracksSince
    ) {
        if (!isSubscriber(user, configuration)) {
            save(
                new UserCheckConfigurationSubscription(
                    user,
                    configuration,
                    tracksSince
                )
            );
        }
    }

    @Override
    public List<HttpAccessibilityCheckResult> findForUser(
        User requester,
        HttpAccessibilityCheckConfiguration configuration,
        ZonedDateTime startDate,
        ZonedDateTime endDate
    ) throws NotExistsException {
        UserCheckConfigurationSubscription subscription;
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException(
                "startDate (" + startDate + ") "
                    + "must be after endDate (" + endDate + ")"
            );
        }
        subscription = findBySubscriberAndConfig(requester, configuration);
        if (subscription == null) {
            throw new NotExistsException(
                requester.getUsername()
                    + " has not subscribed to track the "
                    + configuration.getTargetUrl()
                    + " accessibility"
            );
        }
        try (Session session = sessionFactory.openSession()) {
            return (List<HttpAccessibilityCheckResult>) session
                .createQuery(
                    "from HttpAccessibilityCheckResult hacr "
                        + "where hacr.configuration = :configuration "
                        + "and hacr.checkedWhen between :startDate and :endDate"
                )
                .setParameter(
                    "configuration",
                    configuration
                )
                .setParameter(
                    "startDate",
                    subscription.getTrackedSince().isAfter(startDate)
                        ? subscription.getTrackedSince()
                        : endDate
                )
                .setParameter(
                    "endDate",
                    endDate
                )
                .list();
        }
    }

    // CONTRACT: if found nothing - returns null
    private UserCheckConfigurationSubscription findBySubscriberAndConfig(
        User subscriber,
        HttpAccessibilityCheckConfiguration configuration
    ) {
        UserCheckConfigurationSubscription subscription = null;
        try (Session session = sessionFactory.openSession()) {
            List<UserCheckConfigurationSubscription> subscriptions =
                session
                    .createQuery(
                        "from UserCheckConfigurationSubscription uccs "
                            + "where uccs.trackedBy = :user "
                            + "and uccs.trackedConfiguration = :configuration"
                    )
                    .setParameter("user", subscriber)
                    .setParameter("configuration", configuration)
                    .list();
            if (subscriptions.size() != 0) {
                subscription = subscriptions.get(0);
            }
        }
        return subscription;
    }

    private boolean isSubscriber(
        User user,
        HttpAccessibilityCheckConfiguration configuration
    ) {
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery(
                    "from UserCheckConfigurationSubscription ucca "
                        + "where ucca.trackedBy = :user "
                        + "and ucca.trackedConfiguration = :configuration"
                )
                .setParameter("user", user)
                .setParameter("configuration", configuration)
                .list().size() != 0;
        }
    }

    private boolean isHttpOrHttpsProtocol(URL url) {
        return
            Pattern.compile("^http(s)?$", Pattern.CASE_INSENSITIVE)
                .matcher(
                    url.getProtocol()
                )
                .matches();
    }

    private void save(Object entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.save(entity);
            session.flush();
            t.commit();
        }
    }

    private JobDetail createQuartzJobDetailsFor(
        HttpAccessibilityCheckConfiguration configuration
    ) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(
            CONFIGURATION_JOB_CONTEXT_KEY,
            configuration
        );
        return JobBuilder
            .newJob(activeTrackerJobClass)
            .withIdentity(
                QUARTZ_JOB_NAMING_STRATEGY.apply(configuration),
                ACTIVE_TRACKER_JOBS_GROUP_NAME
            )
            .setJobData(jobDataMap)
            .build();
    }

    private Trigger createTriggerFor(
        HttpAccessibilityCheckConfiguration configuration,
        JobDetail jobDetail
    ) {
        return TriggerBuilder
            .newTrigger()
            .withIdentity(
                QUARTZ_TRIGGER_NAMING_STRATEGY.apply(jobDetail),
                ACTIVE_TRACKER_TRIGGERS_GROUP_NAME
            )
            .withSchedule(
                SimpleScheduleBuilder
                    .simpleSchedule()
                    .withIntervalInSeconds(
                        (int) configuration.getInterval().getSeconds()
                    )
                    .repeatForever()
            )
            .build();
    }

    private void scheduleQrtzJob(JobDetail jobDetail, Trigger trigger)
    throws SchedulerException {
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
