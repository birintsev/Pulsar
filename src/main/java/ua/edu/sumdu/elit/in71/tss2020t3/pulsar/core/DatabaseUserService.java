package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * A default implementation of {@link UserService} that works with users stored
 * in an application database
 * */
public class DatabaseUserService implements UserService {

    private static final Logger LOGGER = Logger.getLogger(
        DatabaseUserService.class
    );

    private final SessionFactory sessionFactory;

    private final Validator validator;

    /**
     * Creates an instance with provided session factory
     *
     * @param sessionFactory    a session factory that will be used
     *                          during interacting with a database
     * */
    public DatabaseUserService(SessionFactory sessionFactory) {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.sessionFactory = sessionFactory;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public User registerUser(User user) {
        if (!isUserValid(user)) {
            throw new IllegalArgumentException("The user in not valid");
        }
        if (exists(user.getId())) {
            throw new IllegalArgumentException("The user already exists");
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            session.flush();
            transaction.commit();
            LOGGER.trace(
                "The user has been registered:" + System.lineSeparator() + user
            );
        }
        return user;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean isActive(User.UserID userID) {
        return true;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean exists(User.UserID userID) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userID) != null;
        }
    }

    private boolean isUserValid(User user) {
        Set<ConstraintViolation<User>> violations =
            validator.validate(user);
        if (violations.size() == 0) {
            return true;
        } else {
            LOGGER.error(
                "User is not valid:" + System.lineSeparator()
                    + user + System.lineSeparator()
                    + "Constraints violations:" + System.lineSeparator()
                    + violations
            );
            return false;
        }
    }
}
