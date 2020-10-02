package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;

import java.io.Serializable;

/**
 * This is a default implementation of {@link ClientHostStatisticService}
 * */
public class ClientHostStatisticServiceImpl implements ClientHostStatisticService {

	private final SessionFactory sessionFactory;

	private static final Logger LOGGER = Logger.getLogger(ClientHostStatisticServiceImpl.class);

	public ClientHostStatisticServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(ClientHostStatistic clientHostStatistic) {
		Serializable savedClientHostStatistic;
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			session.save(clientHostStatistic.getID().getClientHost());
			savedClientHostStatistic = session.save(clientHostStatistic);
			session.flush();
			transaction.commit();
		}
		LOGGER.trace(
			"ClientHostStatistic object (below) is saved:" + System.lineSeparator() + savedClientHostStatistic
		);
	}
}
