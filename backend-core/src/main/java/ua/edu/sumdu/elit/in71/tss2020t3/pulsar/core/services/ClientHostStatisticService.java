package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.time.ZonedDateTime;
import java.util.List;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.businesslogic.UserAccessException;

/**
 * This service-layer interface was designed to represent basic operations
 * on {@link ClientHostStatistic} instances.
 * It is expected to be a common way to perform manipulations on the objects.
 *
 * @see ClientHostStatistic
 * */
public interface ClientHostStatisticService {

    /**
     * Saves passed object to the database.
     * If its database representation does not exist,
     * this method will create it.
     * In other case, the method will update existing records.
     *
     * @param clientHostStatistic   an instance of informational container
     *                              to be persist
     * */
    void save(ClientHostStatistic clientHostStatistic);

    /**
     * Searches for all the {@link ClientHostStatistic} inputs of a
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
     * that is associated with passed {@code publicKey}
     * from passed time interval.
     * <p>
     * Note, that there could be too much stored statistic,
     * so use this method when it's really necessary.
     *
     * @param     requester                a person who is reaching
     *                                     the statistic
     * @param     publicKey                a client's host public key
     * @param     from                     the beginning of the period
     * @param     to                       the end of the period
     * @return                             all the stored client's host
     *                                     statistic from the passed interval
     *                                     or an empty list if the client's host
     *                                     doesn't exist
     * @throws    UserAccessException      if the user is not authorized to view
     *                                     the statistic of the client host
     *                                     pointed by passed {@code publicKey}
     * @exception IllegalArgumentException if {@code from} is equal to
     *                                     {@code to}
     *                                     or represents a timestamp
     *                                     after {@code to}
     * */
    List<ClientHostStatistic> getByPublicKey(
        User requester,
        String publicKey,
        ZonedDateTime from,
        ZonedDateTime to
    ) throws UserAccessException;
}
