package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.List;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;

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
     * that is associated with passed {@code publicKey}.
     * <p>
     * Note, that there could be too much stored statistic,
     * so use this method when it's really necessary.
     *
     * @param  publicKey a client's host public key
     * @return all the stored client's host statistic
     *         or an empty list if the client's host doesn't exist
     * */
    List<ClientHostStatistic> getByPublicKey(String publicKey);
}
