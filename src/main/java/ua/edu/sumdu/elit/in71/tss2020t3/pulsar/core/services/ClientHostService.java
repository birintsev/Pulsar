package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.Set;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.busineeslogic.AlreadyExistsException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.busineeslogic.UserStatusException;

public interface ClientHostService {

    /**
     * Creates a {@link ClientHost} based on a user request.
     * <p>
     * In this business case the requester will be an owner
     * of a new {@link ClientHost}
     *
     * @param     request   a POJO representation of a user request
     *                      for creating a new {@link ClientHost}
     * @param     requester a requester and an owner of new {@link ClientHost}
     * @return              created {@link ClientHost}
     * @throws   AlreadyExistsException
     *                      if there already exists a host with such private key
     * @throws   UserStatusException
     *                      if the {@link User user} has reached the limit
     *                      of owned {@link ClientHost ClientHosts}
     * @exception javax.validation.ConstraintViolationException
     *                      if passed {@code request} is not valid
     * @exception IllegalArgumentException
     *                      if {@code owner} does not exist
     *                      or passed object is not valid
     *
     * */
    ClientHost createForUserRequest(
        CreateClientHostDTO request, // todo refactor this parameter
        User requester // todo because it is a part of representational layer
    ) throws AlreadyExistsException, UserStatusException;

    /**
     * Searches en entity of {@link ClientHost} by its public key
     *
     * @param  publicKey a client's host public key
     * @return           a {@link ClientHost} associated with the
     *                   {@code publicKey} or {@code null} if it does not exist
     * */
    ClientHost getByPublicKey(String publicKey);

    /**
     * Searches en entity of {@link ClientHost} by its private key
     *
     * @param  privateKey a client's host private key
     * @return            a {@link ClientHost} associated with the
     *                    {@code privateKey} or {@code null} if it
     *                    does not exist
     * */
    ClientHost getByPrivateKey(String privateKey);

    /**
     * Searches all the {@link ClientHost} owned by passed {@link User}
     * <p>
     * If passed {@code owner} does not exist, this method returns an empty set
     *
     * @param  owner an owner of {@link ClientHost}s
     * @return       a set of {@link ClientHost}s owned by passed {@link User}
     * */
    Set<ClientHost> getByOwner(User owner);

    /**
     * Subscribes a {@link User} to the {@link ClientHost}
     * associated with passed {@code publicKey}
     * <p>
     * After this operation, {@code user} will be able to review the information
     * about the host as well as its statistic.
     *
     * @param     publicKey a {@link ClientHost#getPublicKey() publicKey}
     *                      of a host to be added
     * @param     user      a {@link User} who should be able to review
     *                      the host statistic
     * @throws    UserStatusException
     *                      if the {@link User user} has reached the limit
     *                      of owned/subscribed {@link ClientHost ClientHosts}
     * @exception java.util.NoSuchElementException
     *                      if there is not such {@link ClientHost}
     *                      associated with passed {@code publicKey}
     * */
    void subscribeByPublicKey(String publicKey, User user)
        throws UserStatusException;

    /**
     * Searches all the {@link ClientHost} subscribed by passed {@link User}
     * <p>
     * If passed {@code subscriber} does not exist,
     * this method returns an empty set
     *
     * @param  subscriber a subscriber of {@link ClientHost}s
     * @return            a set of {@link ClientHost}s
     *                    subscribed by passed {@link User}
     * */
    Set<ClientHost> getBySubscriber(User subscriber);

    /**
     * Returns {@code true} if the {@code user} is a subscriber or an owner
     * of the {@link ClientHost} associated with
     * passed {@link ClientHost#getPublicKey() publicKey}
     *
     * @param  user      a user
     * @param  publicKey a {@link ClientHost}'s
     *                   {@link ClientHost#getPublicKey() publicKey}
     * @return           {@code true} if the {@code user} is an owner
     *                   of the {@link ClientHost} associated with
     *                   the {@code publicKey} or is subscribed to the host
     * */
    boolean subscriberOrOwner(User user, String publicKey);

    /**
     * Returns all the existing {@link ClientHost client hosts}
     *
     * @return all the existing {@link ClientHost client hosts}
     * */
    Set<ClientHost> getAll();
}
