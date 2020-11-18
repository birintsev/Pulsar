package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMATION_PENDING;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.USER_STATUS_REGISTRATION_CONFIRMED;


import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest;
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

    public static final String USER_STATUS_FREE_ACCOUNT = "FREE_ACCOUNT";

    public static final String USER_STATUS_PREMIUM_ACCOUNT = "PREMIUM_ACCOUNT";

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
        user.getUserStatuses().addAll(
            Arrays.asList(
                new UserStatus(
                    System.getProperty(
                        USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
                    )
                ),
                new UserStatus(USER_STATUS_FREE_ACCOUNT)
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
            for (UserStatus userStatus : user.getUserStatuses()) {
                session.save(userStatus);
            }
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

    @Override
    public UserResetPasswordRequest getLatestUnusedRequestOf(User user) {
        String selectHQL = "from UserResetPasswordRequest urpr"
            + " where urpr.user = :user"
            + " and urpr.resetWhen is null"
            + " order by urpr.createdWhen desc";
        List<UserResetPasswordRequest> requests;
        try (Session session = sessionFactory.openSession()) {
            requests = (List<UserResetPasswordRequest>)
                session
                    .createQuery(selectHQL)
                    .setParameter("user", user)
                    .setMaxResults(1).list();
        }
        return requests.size() == 0 ? null : requests.get(0);
    }

    @Override
    public void resetPasswordByRequest(
        UUID resetKey, String newPassword
    ) {
        User user;
        UserResetPasswordRequest resetPasswordRequest = findByKey(resetKey);
        if (resetPasswordRequest == null) {
            throw new IllegalArgumentException("Passed key doesn't exist");
        }
        if (resetPasswordRequest.getResetWhen() != null) {
            throw new IllegalArgumentException(
                "Passed key has been already used"
            );
        }
        user = resetPasswordRequest.getUser();
        String oldPassword = user.getPassword();
        UserResetPasswordRequest resetRequest = getLatestUnusedRequestOf(user);
        if (resetRequest == null) {
            throw new IllegalStateException(
                "The user has not submitted a request ot reset his/her password"
            );
        }
        if (!passwordFitsRequirements(newPassword)) {
            throw new IllegalArgumentException(
                "New password does not fit all the requirements"
            );
        }
        user.setPassword(newPassword);
        resetRequest.setResetWhen(ZonedDateTime.now());
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.update(user);
            session.update(resetRequest);
            session.flush();
            t.commit();
        } catch (Exception e) {
            LOGGER.error(
                "Error during user's (email = "
                    + user.getId().getEmail()
                    + ") password reset"
            );
            user.setPassword(oldPassword);
            throw e;
        }
    }

    @Override
    public UserResetPasswordRequest createResetPasswordRequestFor(User user) {
        UserResetPasswordRequest resetPasswordRequest;
        if (!exists(user.getId())) {
            throw new IllegalArgumentException("The user does not exist");
        }
        resetPasswordRequest = getLatestUnusedRequestOf(user);
        if (resetPasswordRequest != null) {
            return resetPasswordRequest;
        } else {
            try (Session session = sessionFactory.openSession()) {
                Transaction t = session.beginTransaction();
                session.save(
                        new UserResetPasswordRequest(
                            null,
                            user,
                            ZonedDateTime.now(),
                            null
                        )
                    );
                session.flush();
                t.commit();
            }
            resetPasswordRequest = getLatestUnusedRequestOf(user);
        }
        return resetPasswordRequest;
    }

    @Override
    public UserResetPasswordRequest findByKey(UUID resetKey) {
        try (Session session = sessionFactory.openSession()) {
            return (UserResetPasswordRequest) session
                .createQuery(
                    "from UserResetPasswordRequest urpr "
                        + "where urpr.resetKey = :resetKey"
                )
                .setParameter("resetKey", resetKey)
                .getSingleResult();
        }
    }

    @Override
    public void addUserStatus(User user, UserStatus userStatus) {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            user.getUserStatuses().add(userStatus);
            session.update(user);
            session.flush();
            t.commit();
        } catch (ConstraintViolationException e) {
            LOGGER.error(e);
            throw new NoSuchElementException(
                e.getMessage() + ": "
                    + Optional
                    .ofNullable(e.getCause())
                    .orElse(new SQLException())
                    .getMessage()
            );
        } finally {
            user.getUserStatuses().remove(userStatus);
        }
    }

    @Override
    public Set<UserStatus> getAllStatuses() {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(
                (List<UserStatus>) session
                    .createQuery("from UserStatus")
                    .list()
            );
        }
    }

    @Override
    public void removeStatus(User user, UserStatus userStatus) {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            user.getUserStatuses().remove(userStatus);
            session.update(user);
            session.flush();
            t.commit();
        }
    }

    private boolean passwordFitsRequirements(String password) {
        Pattern userPasswordPattern = Pattern.compile(
            System.getProperty(ApplicationPropertiesNames.USER_PASSWORD_REGEXP)
        );
        return userPasswordPattern.matcher(password).matches();
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

    private boolean existsByEmailAndUsername(String email, String username) {
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery(
                    "from User u"
                        + " where u.username = :username"
                        + " or u.id.email = :email"
                )
                .setParameter("email", email)
                .setParameter("username", username)
                .list().size() > 0;
        }
    }
}
