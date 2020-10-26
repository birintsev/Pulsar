package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;

/**
 * This is a default implementation of {@link ClientHostStatisticService}
 *
 * @author Mykhailo Birintsev
 * */
public class ClientHostStatisticServiceImpl
    implements ClientHostStatisticService {

    private final SessionFactory sessionFactory;

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostStatisticServiceImpl.class
    );

    /**
     * Creates an instance with provided session factory
     *
     * @param sessionFactory    a session factory that will be used
     *                          during interacting with a database
     * */
    public ClientHostStatisticServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void save(ClientHostStatistic clientHostStatistic) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(clientHostStatistic.getId().getClientHost());
            session.saveOrUpdate(clientHostStatistic);
            session.flush();
            transaction.commit();
            LOGGER.trace(
                "Transaction committed for saving passed clientHostStatistic: "
                    + System.lineSeparator() + clientHostStatistic
            );
        }
    }
}
