package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.FREE_ACCOUNT_STORED_STATISTIC_DAYS;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserAccessException;

/**
 * This is a default implementation of {@link ClientHostStatisticService}
 *
 * @author Mykhailo Birintsev
 * */
public class ClientHostStatisticServiceImpl
implements ClientHostStatisticService {

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostStatisticServiceImpl.class
    );

    private final SessionFactory sessionFactory;

    private final ClientHostService clientHostService;

    private final UserService userService;

    /**
     * Creates an instance with provided session factory
     * @param sessionFactory    a session factory that will be used
     *                          during interacting with a database
     * @param clientHostService s service for reaching client host service
     * @param userService       a service for working with users
     * */
    public ClientHostStatisticServiceImpl(
        SessionFactory sessionFactory,
        ClientHostService clientHostService,
        UserService userService) {
        this.sessionFactory = sessionFactory;
        this.clientHostService = clientHostService;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void save(ClientHostStatistic clientHostStatistic) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(clientHostStatistic);
            session.flush();
            transaction.commit();
            LOGGER.trace(
                "Transaction committed for saving passed clientHostStatistic: "
                    + System.lineSeparator() + clientHostStatistic
            );
        }
    }

    @Override
    public List<ClientHostStatistic> getByPublicKey(
        User requester,
        String publicKey,
        ZonedDateTime from,
        ZonedDateTime to
    ) throws UserAccessException {
        if (
            !clientHostService.subscriberOrOwner(
                requester,
                publicKey
            )
        ) {
            throw new UserAccessException(
                requester,
                clientHostService.getByPublicKey(publicKey),
                "The user is not an owner or subscriber of the host"
            );
        }
        if (!userService.isUserPremiumAccount(requester)) {
            from = ZonedDateTime.now().minusDays(
                FREE_ACCOUNT_STORED_STATISTIC_DAYS
            );
        }
        try (Session session = sessionFactory.openSession()) {
            return (List<ClientHostStatistic>)
                session
                    .createQuery(
                        "from ClientHostStatistic s where "
                            + "s.id.clientHost.publicKey = :publicKey "
                            + "and s.id.clientLocalTime between :from and :to")
                    .setParameter(
                        "publicKey",
                        publicKey
                    )
                    .setParameter(
                        "from",
                        from.withZoneSameLocal(ZoneOffset.UTC)
                    )
                    .setParameter(
                        "to",
                        to.withZoneSameLocal(ZoneOffset.UTC)
                    )
                    .list();
        }
    }
}
