package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a container entity of load average information
 * received from an agent
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoadAverage implements Serializable {

    private ClientHostStatistic clientHostStatistic;

    private Integer order;

    private Double loadAverage;

    @Override
    public String toString() {
        return "LoadAverage{"
            + "clientHostStatistic=" + clientHostStatistic.getId()
            + ", order=" + order
            + ", loadAverage=" + loadAverage
            + '}';
    }
}
