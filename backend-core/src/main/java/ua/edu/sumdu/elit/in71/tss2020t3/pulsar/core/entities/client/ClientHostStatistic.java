package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

/**
 * This class represents an entity, that is a container of information portion
 * received from an agent
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientHostStatistic implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostStatistic.class
    );

    private ClientHostStatisticID id;

    private String host;

    private ZonedDateTime bootTime;

    private String agentVersion;

    private Set<LoadAverage> loadAverage;

    private Set<CPUInfo> cpuInfoList;

    private Set<DiskInfo> disksInfo;

    private MemoryInfo memoryInfo;

    private Set<NetworkInfo> networksInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientHostStatistic that = (ClientHostStatistic) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientHostStatisticID implements Serializable {

        private ClientHost clientHost;

        private ZonedDateTime clientLocalTime;

        @Override
        public String toString() {
            return "ClientHostStatisticID{"
                + "clientHost=" + clientHost.getPublicKey()
                + ", clientLocalTime=" + clientLocalTime
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

            ClientHostStatisticID that = (ClientHostStatisticID) o;

            if (!Objects.equals(clientHost, that.clientHost)) {
                return false;
            }
            return Objects.equals(clientLocalTime, that.clientLocalTime);
        }

        @Override
        public int hashCode() {
            int result = clientHost != null ? clientHost.hashCode() : 0;
            result = 31 * result
                + (clientLocalTime != null ? clientLocalTime.hashCode() : 0);
            return result;
        }
    }
}
