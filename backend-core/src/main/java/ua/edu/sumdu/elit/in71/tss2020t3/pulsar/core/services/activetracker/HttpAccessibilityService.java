package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker;

import java.net.URL;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.NotExistsException;

/**
 * This service is a business logic provider for operations
 * performed on {@link HttpAccessibilityCheckConfiguration configuration items}
 * for {@link HttpAccessibilityCheckResult accessibility check events}
 *
 * @see HttpAccessibilityCheckResult
 * @see HttpAccessibilityCheckConfiguration
 * */
public interface HttpAccessibilityService {

    // todo add '...The support of pausing and stopping active checks...'
    /**
     * Creates a configuration item for HTTP/HTTPS check events
     * <p>
     * The words 'creates a configuration' here mean that the configuration
     * will be effectively activated.
     * So that, it's expected that an application submodule
     * responsible for performing active checks will consider
     * a new configuration item as <i>active</i>.
     * The support of pausing and stopping active checks
     * will be added on demand in the future.
     *
     * @param     targetUrl                a destination address
     *                                     (HTTP/HTTPS-based) to be checked
     *                                     by a scheduled job for a new
     *                                     {@link HttpAccessibilityCheckConfiguration configuration item}
     * @param     responseTimeout          a response timeout after which
     *                                     the host will be considered as
     *                                     unavailable
     * @param     interval                 a time period between two
     *                                     subsequent checks
     * @return                             a new configuration item for tracking
     *                                     HTTP/HTTPS accessibility
     *                                     of the {@code targetUrl}
     * @throws    AlreadyExistsException   if a configuration
     *                                     for the {@code targetUrl}
     *                                     already exist
     * @exception IllegalArgumentException if the {@code targetUrl} protocol
     *                                     is neither HTTP nor HTTPS
     * */
    HttpAccessibilityCheckConfiguration create(
        URL targetUrl,
        Duration responseTimeout,
        Duration interval
    ) throws AlreadyExistsException;

    /**
     * Searches for a {@link HttpAccessibilityCheckConfiguration} with specified
     * {@link HttpAccessibilityCheckConfiguration#getTargetUrl targetUrl}
     *
     * @param  targetUrl searched URL
     * @return           a configuration item for passed URL
     *                   or {@code null} if it does not exist
     * */
    HttpAccessibilityCheckConfiguration findByTargetUrl(URL targetUrl);

    /**
     * Saves a performed {@link HttpAccessibilityCheckResult check result}
     * to the database
     *
     * @param checkResult a preformed
     *                    {@link HttpAccessibilityCheckResult check result}
     * */
    void save(HttpAccessibilityCheckResult checkResult);

    /**
     * This method provides a possibility to register a new configuration item
     * for executing regular active HTTP/HTTPS accessibility checks
     * ONLY in case when passed {@code targetUrl} is not already tracked
     *
     * @param  targetUrl                   a destination address
     *                                     (HTTP/HTTPS-based) to be checked
     *                                     by a scheduled job for a new
     *                                     {@link HttpAccessibilityCheckConfiguration
     *                                     configuration item}
     * @param  responseTimeout             a response timeout after which
     *                                     the host will be considered as
     *                                     unavailable
     * @param  interval                    a time period between two
     *                                     subsequent checks
     * @return                             a new configuration item for tracking
     *                                     HTTP/HTTPS accessibility of the {@code targetUrl}
     *                                     or the existing one if it exists
     *                                     (<strong>NOTE</strong> that in this case
     *                                     the {@code interval} and {@code responseTimeout}
     *                                     are not updated in existing configuration)
     * @exception IllegalArgumentException if the interval is less than
     *                                     {@link HttpAccessibilityCheckConfiguration#MIN_CHECK_INTERVAL}
     * @see                                #create(URL, Duration, Duration)
     * */
    HttpAccessibilityCheckConfiguration createIfNotExist(
        URL targetUrl,
        Duration responseTimeout,
        Duration interval
    );

    /**
     * Subscribes passed user
     * to track the {@code configuration}
     * since specified date.
     * <p>
     * The words 'subscribes since' here means that only that part
     * of stored {@link HttpAccessibilityCheckResult check results}
     * will be available for he/she
     * that was received before {@code tracksSince} date
     * <p>
     * If the user is a subscriber of the {@code configuration},
     * no updates will be performed
     *
     * @param user          a user to be subscribed
     * @param configuration a configuration to be subscribed
     * @param tracksSince   a start date of active tracking
     *                      the host accessibility by the user
     * */
    void subscribe(
        User user,
        HttpAccessibilityCheckConfiguration configuration,
        ZonedDateTime tracksSince
    );

    /**
     * Finds all the {@link HttpAccessibilityCheckResult check results}
     * which are available for the {@link User requester}
     * taking into account the following parameters:
     * <ol>
     *     <li>
     *         The {@code startDate} -
     *         all the {@link HttpAccessibilityCheckResult check results}
     *         performed before this date will be filtered out
     *     </li>
     *     <li>
     *         The {@code endDate} -
     *         all the {@link HttpAccessibilityCheckResult check results}
     *         performed after this date will be filtered out
     *     </li>
     *     <li>
     *         The {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.UserCheckConfigurationSubscription#getTrackedSince
     *         date}, since which the {@link User requester} has begun to track
     *         the availability of the {@link
     *         HttpAccessibilityCheckConfiguration#getTargetUrl target url}.
     *         So that, the <strong>effective</strong> {@code startDate} is
     *         the earliest date from the <strong>passed</strong> {@code startDate}
     *         and {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.UserCheckConfigurationSubscription#getTrackedSince
     *         the date since which the user tracks the host availability}
     *     </li>
     * </ol>
     *
     * @param  requester          a user who has submitted a request
     *                            to track the
     *                            {@link HttpAccessibilityCheckConfiguration#getTargetUrl
     *                            host} availability
     * @param  configuration      the host availability tracking configuration
     * @param  startDate          the first date for filtering result set
     * @param  endDate            the latest date for filtering result set
     * @return                    results of the host availability checks
     *                            performed during the time period
     *                            determined according to the logic
     *                            described above
     * @throws NotExistsException if the user has not submitted a request
     *                            to track the host availability yet
     * */
    List<HttpAccessibilityCheckResult> findForUser(
        User requester,
        HttpAccessibilityCheckConfiguration configuration,
        ZonedDateTime startDate,
        ZonedDateTime endDate
    ) throws NotExistsException;

    /**
     * Finds all the hosts subscribed by passed user
     *
     * @param  user a user whose subscriptions are requested
     * @return      a set of hosts tracking configuration items
     *              which are tracked by the user
     *
     * @see         HttpAccessibilityCheckConfiguration#getTargetUrl
     * @see         ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.UserCheckConfigurationSubscription
     * */
    Set<HttpAccessibilityCheckConfiguration> getUserSubscriptions(User user);
}
