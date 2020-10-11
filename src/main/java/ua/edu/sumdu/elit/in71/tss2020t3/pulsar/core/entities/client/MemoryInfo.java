package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * This class represents a container entity
 * of memory-specific pieces of information received from an agent
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemoryInfo implements Serializable {

    private ClientHostStatistic clientHostStatistic;

    private BigInteger wired;

    private BigInteger free;

    private BigInteger active;

    private BigInteger inactive;

    private BigInteger total;

    @Override
    public String toString() {
        return "MemoryInfo{"
            + "clientHostStatistic=" + clientHostStatistic.getId()
            + ", wired=" + wired
            + ", free=" + free
            + ", active=" + active
            + ", inactive=" + inactive
            + ", total=" + total
            + '}';
    }
}
