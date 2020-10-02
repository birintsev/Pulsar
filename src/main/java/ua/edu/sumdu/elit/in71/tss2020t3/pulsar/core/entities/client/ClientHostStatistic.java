package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Set;

/**
 * This class represents an entity, that is a container of information portion received from an agent.
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientHostStatistic implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(ClientHostStatistic.class);

	private ClientHostStatisticID ID;

	private URI host;

	private Timestamp bootTime;

	private String agentVersion;

	private Set<LoadAverage> loadAverage;

	private Set<CPUInfo> CPUInfoList;

	private Set<DiskInfo> disksInfo;

	private MemoryInfo memoryInfo;

	private Set<NetworkInfo> networksInfo;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientHostStatistic that = (ClientHostStatistic) o;

		return ID != null ? ID.equals(that.ID) : that.ID == null;
	}

	@Override
	public int hashCode() {
		return ID != null ? ID.hashCode() : 0;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ClientHostStatisticID implements Serializable {

		private ClientHost clientHost;

		private Timestamp clientLocalTime;

		@Override
		public String toString() {
			return "ClientHostStatisticID{" +
				"clientHost=" + clientHost.getPublicKey() +
				", clientLocalTime=" + clientLocalTime +
				'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ClientHostStatisticID that = (ClientHostStatisticID) o;

			if (clientHost != null ? !clientHost.equals(that.clientHost) : that.clientHost != null) {
				return false;
			}
			return clientLocalTime != null ?
				clientLocalTime.equals(that.clientLocalTime) : that.clientLocalTime == null;
		}

		@Override
		public int hashCode() {
			int result = clientHost != null ? clientHost.hashCode() : 0;
			result = 31 * result + (clientLocalTime != null ? clientLocalTime.hashCode() : 0);
			return result;
		}
	}

}
