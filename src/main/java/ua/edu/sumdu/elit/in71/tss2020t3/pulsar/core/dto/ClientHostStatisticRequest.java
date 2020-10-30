package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A POJO that represents a GET request of
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic}
 * associated with exact
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientHostStatisticRequest {

    /**
     * A public key associated with destination
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
     * */
    private String publicKey;

    private int pageSize;

    private int startPage;
}
