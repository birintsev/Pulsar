package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.math.BigInteger;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Calculates basic system metrics such as
 * {@link #getTotalActiveChecksNumber amount of requested active HTTP/HTTPS checks},
 * {@link #getTotalSentEmailsNumber amount of sent emails}
 * or {@link #getActiveClientHostsNumber active client hosts amount}
 * for generating admin statistic
 * */
public interface AdminDashboardService {

    /**
     * The time last after the latest
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic portion of statistic}
     * has been received for a
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
     * (associated with the statistic input)
     * to be considered active
     * */
    Duration CLIENT_HOST_MAX_ACTIVE_DURATION =
        Duration.ofMinutes(3);

    /**
     * Calculates amount of all users within the system
     *
     * @return a total number of created users
     * */
    BigInteger getTotalUsersNumber();

    /**
     * Calculates amount of all users within the system that had submitted
     * a registration request before specified date
     *
     * @param before a date, after which users registration confirmations
     *               are not took into account
     * @return       a total number of created users
     * */
    BigInteger getTotalUsersNumber(ZonedDateTime before);

    /**
     * Calculates amount of users, that have confirmed their emails
     *
     * @return a total number of users that have their registration
     * @see    ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMED
     * */
    BigInteger getActiveUsersNumber();

    /**
     * Calculates amount of users, that have confirmed their emails
     *
     * @param  before a date, after which users registration confirmations
     *                are not took into account
     * @return        a total number of users that have their registration
     * @see           ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMED
     * */
    BigInteger getActiveUsersNumber(ZonedDateTime before);

    /**
     * Calculates amount of all created {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost ClientHosts}
     *
     * @return a total number of created {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost ClientHost user servers associated with agents}
     * */
    BigInteger getTotalClientHostsNumber();

    /**
     * Calculates amount of all active client hosts.
     * <p>
     * A client host is considered 'active' if a valid portion of
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic statistic}
     * has been received not earlier than
     * {@link #CLIENT_HOST_MAX_ACTIVE_DURATION} ago.
     *
     * @return amount of active {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost client hosts}
     * */
    BigInteger getActiveClientHostsNumber();

    /**
     * Calculates amount of all active client hosts.
     * <p>
     * A client host is considered 'active' if a valid portion of
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic statistic}
     * has been received not earlier than
     * {@link #CLIENT_HOST_MAX_ACTIVE_DURATION} ago.
     *
     * @param  from start date
     * @param  to   end date
     * @return      amount of active {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost client hosts}
     * */
    BigInteger getActiveClientHostsNumber(ZonedDateTime from, ZonedDateTime to);

    /**
     * Calculates the total number of sent emails
     * <p>
     * Note, that this method does not take into account
     * an email delivery status.
     * So, those emails, that have not been delivered are count, too.
     *
     * @return a total amount of the emails been sent
     * */
    BigInteger getTotalSentEmailsNumber();

    /**
     * Calculates the total number of sent emails
     * <p>
     * Note, that this method does not take into account
     * an email delivery status.
     * So, those emails, that have not been delivered are count, too.
     *
     * @param from start date
     * @param to   end date
     * @return a total amount of the emails been sent
     * */
    BigInteger getTotalSentEmailsNumber(ZonedDateTime from, ZonedDateTime to);

    /**
     * Calculates a total number of availability checks by HTTP or HTTPS
     * <p>
     * Note, that this mehod does not take into account the HTTP statuses,
     * returned by checked hosts.
     * So that, not only
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult checks}
     * with success statuses are taken into account,
     * but those ones which have received other (e.g 404 NOT FOUND) status
     * or have not received a response at all.
     *
     * @return a total count of registered {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult}
     * @see    ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult
     * */
    BigInteger getTotalActiveChecksNumber();

    /**
     * Calculates a total number of availability checks by HTTP or HTTPS
     * <p>
     * Note, that this mehod does not take into account the HTTP statuses,
     * returned by checked hosts.
     * So that, not only
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult checks}
     * with success statuses are taken into account,
     * but those ones which have received other (e.g 404 NOT FOUND) status
     * or have not received a response at all.
     *
     * @param  from start date
     * @param  to   end date
     * @return      a total count of registered {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult}
     * @see         ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult
     * */
    BigInteger getTotalActiveChecksNumber(ZonedDateTime from, ZonedDateTime to);
}
