package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.AlreadyExistsException;

public class ClientHostServiceImpl implements ClientHostService {

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostServiceImpl.class
    );

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
    public ClientHost getByPrivateKey(UUID privateKey) {
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
}
