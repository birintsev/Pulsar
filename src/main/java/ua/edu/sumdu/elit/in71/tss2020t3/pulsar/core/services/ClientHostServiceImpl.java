package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus.USER_STATUS_PREMIUM_ACCOUNT;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserSubscription;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException;

public class ClientHostServiceImpl implements ClientHostService {

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostServiceImpl.class
    );

    public static final int FREE_ACCOUNT_MAX_CLIENT_HOSTS = 2;

    private final UserService userService;

    private final SessionFactory sessionFactory;

    private final Validator validator;

    /**
     * A default constructor
     *
     * @param userService    a service for working with users
     * @param sessionFactory a hibernate session factory that will be used
     *                       during interaction with a configured database
     * @param validator      a javax validator for entities validation
     *                       (e.g. passed data transfer objects)
     * */
    public ClientHostServiceImpl(
        UserService userService,
        SessionFactory sessionFactory,
        Validator validator
    ) {
        this.userService = userService;
        this.sessionFactory = sessionFactory;
        this.validator = validator;
    }

    @Override
    public ClientHost createForUserRequest(
        CreateClientHostDTO request, User requester
    ) {
        ClientHost newClientHost;
        if (userReachedClientHostsLimit(requester)) {
            throw new UserStatusException(
                "The user has already reached "
                    + "subscribed/created client hosts limit"
            );
        }
        Set<ConstraintViolation<CreateClientHostDTO>> validationResult =
            validator.validate(request);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
        if (!arePrivateAndPublicKeysUnique(request)) {
            throw new AlreadyExistsException(
                "Both public and private key must be unique within the system"
            );
        }
        if (!userService.exists(requester.getId())) {
            throw new IllegalArgumentException(
                "The requester does not exist"
            );
        }
        newClientHost = convert(request, requester);
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.save(newClientHost);
            session.flush();
            t.commit();
        }
        LOGGER.trace(
            "New client host "
                + newClientHost.getPublicKey()
                + " saved to the database"
        );
        return newClientHost;
    }

    @Override
    public ClientHost getByPublicKey(String publicKey) {
        try (Session session = sessionFactory.openSession()) {
            return (ClientHost) session
                .createQuery(
                    "from ClientHost ch where ch.publicKey = :publicKey"
                )
                .setString("publicKey", publicKey)
                .uniqueResult();
        }
    }

    @Override
    public ClientHost getByPrivateKey(String privateKey) {
        try (Session session = sessionFactory.openSession()) {
            return (ClientHost) session
                .createQuery(
                    "from ClientHost ch where ch.id.privateKey = :privateKey"
                )
                .setParameter("privateKey", privateKey)
                .uniqueResult();
        }
    }

    @Override
    public Set<ClientHost> getByOwner(User owner) {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(
                (List<ClientHost>)
                    session
                        .createQuery(
                            "from ClientHost c where c.owner = :owner"
                        ).setParameter("owner", owner)
                        .list()
            );
        }
    }

    @Override
    public void subscribeByPublicKey(String publicKey, User user) {
        ClientHost clientHost;
        if (userReachedClientHostsLimit(user)) {
            throw new UserStatusException(
                "The user has already reached "
                    + "subscribed/created client hosts limit"
            );
        }
        clientHost = getByPublicKey(publicKey);
        if (clientHost == null) {
            throw new NoSuchElementException(
                "ClientHost (publicKey = " + publicKey + ") does not exist"
            );
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            session.save(
                new UserSubscription(
                    new UserSubscription.ID(
                        user, clientHost
                    )
                )
            );
            session.flush();
            t.commit();
        }
    }

    @Override
    public Set<ClientHost> getBySubscriber(User subscriber) {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(
                (List<ClientHost>) session
                    .createQuery(
                        "select us.id.clientHost "
                            + "from UserSubscription us "
                            + "where us.id.user = :user"
                    )
                    .setParameter("user", subscriber)
                    .list()
            );
        }
    }

    @Override
    public boolean subscriberOrOwner(User user, String publicKey) {
        String ownerQuery = "select count(*) "
            + "from ClientHost ch "
            + "where ch.publicKey = :publicKey "
            + "and ch.owner = :owner";
        String subscriberQuery = "select count(*) "
            + "from UserSubscription us "
            + "where us.id.clientHost = "
                + "(from ClientHost ch "
                + "where ch.publicKey = :publicKey)"
            + " and us.id.user = :subscriber";
        try (Session session = sessionFactory.openSession()) {
            return (
                ((long) session
                        .createQuery(ownerQuery)
                        .setParameter("owner", user)
                        .setParameter("publicKey", publicKey)
                        .getSingleResult()) > 0
            ) || (
                ((long) session
                        .createQuery(subscriberQuery)
                        .setParameter("subscriber", user)
                        .setParameter("publicKey", publicKey)
                        .getSingleResult()) > 0
            );
        }
    }

    private boolean arePrivateAndPublicKeysUnique(CreateClientHostDTO dto) {
        if (dto.getPrivateKey() == null || dto.getPublicKey() == null) {
            throw new IllegalArgumentException(
                "Public and private keys must be set"
            );
        }
        try (Session session = sessionFactory.openSession()) {
            return session
                .createQuery(
                    "from ClientHost ch "
                        + "where ch.id.privateKey = :privateKey "
                        + "or ch.publicKey = :publicKey"
                )
                .setParameter("publicKey", dto.getPublicKey())
                .setParameter("privateKey", dto.getPrivateKey())
                .list().size() == 0;
        }
    }

    private ClientHost convert(CreateClientHostDTO dto, User user) {
        return new ClientHost(
            new ClientHost.ID(dto.getPrivateKey()),
            dto.getPublicKey(),
            user,
            dto.getName()
        );
    }

    /**
     * Returns count of {@link ClientHost}s owned by passed {@link User}
     *
     * @see ClientHost#getOwner()
     * */
    private int getOwnedClientHostsOf(User user) {
        try (Session session = sessionFactory.openSession()) {
            return Integer.parseInt(
                session
                    .createQuery(
                        "select count(*)"
                            + " from ClientHost ch"
                            + " where ch.owner = :owner"
                    )
                    .setParameter("owner", user)
                    .getSingleResult()
                    .toString()
            );
        }
    }

    /**
     * Returns count of {@link ClientHost}s subscribed by passed {@link User}
     *
     * @see UserSubscription
     * */
    private int getSubscribedClientHostsOf(User user) {
        try (Session session = sessionFactory.openSession()) {
            return Integer.parseInt(
                session
                    .createQuery(
                        "select count(*)"
                            + " from UserSubscription us"
                            + " where us.id.user = :subscriber"
                    )
                    .setParameter("subscriber", user)
                    .getSingleResult()
                    .toString()
            );
        }
    }

    private boolean userReachedClientHostsLimit(User user) {
        // premium account users can add unlimited client hosts
        if (
            user.getUserStatuses().contains(
                new UserStatus(USER_STATUS_PREMIUM_ACCOUNT)
            )
        ) {
            return false;
        }
        int subscribedClientHostsNumber = getSubscribedClientHostsOf(user);
        int ownedClientHostsNumber = getOwnedClientHostsOf(user);
        return subscribedClientHostsNumber + ownedClientHostsNumber
            >= FREE_ACCOUNT_MAX_CLIENT_HOSTS;
    }
}
