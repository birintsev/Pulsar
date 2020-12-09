package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_REGISTRATION_CONFIRMED;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;

/**
 * This is a default implementation for {@link AdminDashboardService}
 * */
@AllArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private static final Logger LOGGER = Logger.getLogger(
        AdminDashboardServiceImpl.class
    );

    private final SessionFactory sessionFactory;

    @Override
    public BigInteger getTotalUsersNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct u) from User u"
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }

    @Override
    public BigInteger getActiveUsersNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct u) "
                            + "from User u "
                            + "where :registrationConfirmedStatus "
                            + "in elements(u.userStatuses)"
                    )
                    .setParameter(
                        "registrationConfirmedStatus",
                        new UserStatus(USER_STATUS_REGISTRATION_CONFIRMED)
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }

    @Override
    public BigInteger getTotalClientHostsNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct ch) from ClientHost ch"
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }

    @Override
    public BigInteger getActiveClientHostsNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct chs.id.clientHost) "
                            + "from ClientHostStatistic chs "
                            + "where chs.id.clientLocalTime >= "
                            + ":lastReceivedStatisticDate"
                    )
                    .setParameter(
                        "lastReceivedStatisticDate",
                        ZonedDateTime
                            .now()
                            .minus(CLIENT_HOST_MAX_ACTIVE_DURATION)
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }

    @Override
    public BigInteger getTotalSentEmailsNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct e) from Email e"
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }

    @Override
    public BigInteger getTotalActiveChecksNumber() {
        try (Session session = sessionFactory.openSession()) {
            return new BigInteger(
                session
                    .createQuery(
                        "select count(distinct ac) "
                            + "from HttpAccessibilityCheckResult ac"
                    )
                    .getSingleResult()
                    .toString()
            );
        }
    }
}
