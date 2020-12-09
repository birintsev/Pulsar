package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a container entity
 * of CPU-specific pieces of information received from an agent
 * */
@NoArgsConstructor
@Data
public class CPUInfo implements Serializable {

    private ClientHostStatistic clientHostStatistic;

    private double num;

    private double user;

    private double system;

    private double idle;

    @Override
    public String toString() {
        return "CPUInfo{"
            + "clientHostStatistic=" + clientHostStatistic.getId()
            + ", num=" + num
            + ", user=" + user
            + ", system=" + system
            + ", idle=" + idle
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CPUInfo cpuInfo = (CPUInfo) o;

        if (Double.compare(cpuInfo.num, num) != 0) {
            return false;
        }
        if (Double.compare(cpuInfo.user, user) != 0) {
            return false;
        }
        if (Double.compare(cpuInfo.system, system) != 0) {
            return false;
        }
        if (Double.compare(cpuInfo.idle, idle) != 0) {
            return false;
        }
        return Objects.equals(clientHostStatistic, cpuInfo.clientHostStatistic);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = clientHostStatistic != null
            ? clientHostStatistic.hashCode()
            : 0;
        temp = Double.doubleToLongBits(num);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(user);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(system);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(idle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
