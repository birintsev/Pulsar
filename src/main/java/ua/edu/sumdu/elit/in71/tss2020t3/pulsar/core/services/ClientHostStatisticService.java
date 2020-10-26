package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

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
}
