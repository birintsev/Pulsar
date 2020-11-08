package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMATION_PENDING;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMED;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException;

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
     * <p>
     * A created user will have initial
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames#USER_STATUS_REGISTRATION_CONFIRMATION_PENDING}
     * status
     * */
    @Override
    public User registerUser(User user) {
        UserRegistrationConfirmation userRegistrationConfirmation;
        boolean existsByEmailAndUsername = existsByEmailAndUsername(
            user.getId().getEmail(), user.getUsername()
        );
        if (!isUserValid(user)) {
            throw new IllegalArgumentException("The user in not valid");
        }
        if (existsByEmailAndUsername) {
            throw new AlreadyExistsException("The user already exists");
        }
        user.getUserStatuses().add(
            new UserStatus(
                System.getProperty(
                    USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
                )
            )
        );
        userRegistrationConfirmation = new UserRegistrationConfirmation(
            new UserRegistrationConfirmation.ID(user),
            ZonedDateTime.now(),
            null,
            null
        );
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            session.save(userRegistrationConfirmation);
            session.flush();
            transaction.commit();
            LOGGER.trace(
                "The user (" + user.getUsername() + ") has been registered"
            );
        }
        return user;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean isActive(User.UserID userID) {
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery(
                    "from User u "
                        + "left join u.userStatuses s"
                        + " where s.status = :status and u.id.email = :email"
                )
                .setString(
                    "status",
                    System.getProperty(USER_STATUS_REGISTRATION_CONFIRMED)
                )
                .setString("email", userID.getEmail())
                .uniqueResult() != null;
        }
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

    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            return (User) session
                .createQuery("from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
        }
    }

    private boolean existsByEmailAndUsername(String email, String username) {
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery(
                    "from User u"
                        + " where u.username = :username"
                        + " and u.id.email = :email"
                )
                .setParameter("email", email)
                .setParameter("username", username)
                .getSingleResult() != null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @exception IllegalStateException if a record about the {@code user}
     *                                  registration does not exist
     * */
    @Override
    public UserRegistrationConfirmation getRegistrationConfirmationFor(
        User user
    ) {
        UserRegistrationConfirmation confirmation;
        try (Session session = sessionFactory.openSession()) {
            confirmation = (UserRegistrationConfirmation)
                session.createQuery(
                    "from UserRegistrationConfirmation confirmation"
                        + " where confirmation.id.user.id.email = :email"
                )
                    .setString("email", user.getId().getEmail())
                    .uniqueResult();
        }
        if (confirmation != null) {
            return confirmation;
        } else if (!exists(user.getId())) {
            throw new IllegalArgumentException("The user does not exist");
        } else {
            String errMsg = "User " + user
                + " exists, but does not have a record about registration "
                + "(see " + UserRegistrationConfirmation.class + ")";
            LOGGER.error(
                "User " + user
                    + " exists, but does not have a record about registration "
                    + "(see " + UserRegistrationConfirmation.class + ")"
            );
            throw new IllegalStateException(errMsg);
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public UserRegistrationConfirmation getRegistrationConfirmationBy(
        UserRegistrationConfirmation.ID key
    ) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(
                session.get(UserRegistrationConfirmation.class, key)
            ).orElseThrow(
                () -> new NoSuchElementException(
                    "No such key associated with a user registration"
                )
            );
        }
    }

    @Override
    public UserRegistrationConfirmation getRegistrationConfirmationBy(
        UUID key
    ) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable((UserRegistrationConfirmation)
                session
                    .createQuery(
                        "from UserRegistrationConfirmation urc"
                            + " where urc.key = :key"
                    )
                    .setParameter("key", key)
                    .uniqueResult()
            ).orElseThrow(
                () -> new NoSuchElementException(
                    "Can not find UserRegistrationConfirmation by passed key"
                )
            );
        }
    }

    @Override
    public void confirmUserRegistration(
        UserRegistrationConfirmation confirmation
    ) {
        User user = confirmation.getId().getUser();
        user.getUserStatuses().remove(
            new UserStatus(
                System.getProperty(
                    USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
                )
            )
        );
        user.getUserStatuses().add(
            new UserStatus(
                System.getProperty(
                    USER_STATUS_REGISTRATION_CONFIRMED
                )
            )
        );
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.update(confirmation);
            session.update(user);
            session.flush();
            t.commit();
        }
        LOGGER.trace(
            "The user registration has been confirmed: " + confirmation
        );
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return (User) session
                .createQuery("from User u where u.id.email = :email")
                .setString("email", email)
                .uniqueResult();
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
