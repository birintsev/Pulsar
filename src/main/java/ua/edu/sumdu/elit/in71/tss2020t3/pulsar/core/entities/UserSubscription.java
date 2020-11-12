package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;

/**
 * This is a Hibernate POJO that represents a many-to-many relationship between
 * a {@link User} and a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}.
 * It indicates that a {@link User} may review the information about the
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost}
 * as well as its
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic statistics}
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSubscription implements Serializable {

    private ID id;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ID implements Serializable {

        private User user;

        private ClientHost clientHost;
    }
}
