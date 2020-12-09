package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_FREE_ACCOUNT;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_PREMIUM_ACCOUNT;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_REGISTRATION_CONFIRMATION_PENDING;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_REGISTRATION_CONFIRMED;

import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;

/**
 * This is a class that loads initial data to the database.
 * <p>
 * If the data already exist, it will not perform any updates.
 * Examples of such data are below:
 * <li>{@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMATION_PENDING}
 * <li>{@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_REGISTRATION_CONFIRMED}
 * <li>{@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_FREE_ACCOUNT}
 * <li>{@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_PREMIUM_ACCOUNT}
 */
public class InitialDataLoader {

    private static final Logger LOGGER = Logger.getLogger(
        InitialDataLoader.class
    );

    /**
     * A default constructor
     *
     * @param sessionFactory         a {@link SessionFactory Hibernate session factory}
     *                               that will be used during interacting
     *                               with configured database
     * @param initDatabaseOnCreation the flag that indicates
     *                               if the {@link #initDatabase()}
     *                               should be invoked automatically
     *                               on the object creation
     * */
    public InitialDataLoader(
        SessionFactory sessionFactory,
        boolean initDatabaseOnCreation
    ) {
        this.sessionFactory = sessionFactory;
        if (initDatabaseOnCreation) {
            initDatabase();
        }
    }

    private final SessionFactory sessionFactory;

    /**
     * This method performs all the necessary actions (runs DML statements)
     * to fill the tables on application startup.
     * Should be run only once.
     * */
    public void initDatabase() {
        LOGGER.trace("Database initialization started");
        List<UserStatus> statusesToInit = Arrays.asList(
            new UserStatus(USER_STATUS_FREE_ACCOUNT),
            new UserStatus(USER_STATUS_PREMIUM_ACCOUNT),
            new UserStatus(USER_STATUS_REGISTRATION_CONFIRMATION_PENDING),
            new UserStatus(USER_STATUS_REGISTRATION_CONFIRMED)
        );
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            for (UserStatus userStatus : statusesToInit) {
                boolean statusExists =
                    session.find(UserStatus.class, userStatus.getStatus())
                        == null;
                if (statusExists) {
                    session.save(userStatus);
                    LOGGER.trace("Creating user status " + userStatus);
                }
            }
            session.flush();
            t.commit();
            LOGGER.trace(
                "Database initialization transaction has been committed"
            );
        } catch (Exception e) {
            LOGGER.error(
                "Error during database initialization. "
                    + "The transaction has rolled back"
                , e
            );
            throw new RuntimeException(e);
        }
    }
}
