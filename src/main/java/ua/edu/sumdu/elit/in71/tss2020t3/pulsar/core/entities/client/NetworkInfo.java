package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *	 This class represents a container entity of network-specific pieces of information received from an agent
 * */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NetworkInfo implements Serializable {

	private ClientHostStatistic clientHostStatistic;

	private String name;

	private BigInteger in;

	private BigInteger out;

	@Override
	public String toString() {
		return "NetworkInfo{" +
			"clientHostStatistic=" + clientHostStatistic.getID() +
			", name='" + name + '\'' +
			", in=" + in +
			", out=" + out +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NetworkInfo that = (NetworkInfo) o;

		if (clientHostStatistic != null ? !clientHostStatistic.equals(that.clientHostStatistic) : that.clientHostStatistic != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (in != null ? !in.equals(that.in) : that.in != null) return false;
		return out != null ? out.equals(that.out) : that.out == null;
	}

	@Override
	public int hashCode() {
		int result = clientHostStatistic != null ? clientHostStatistic.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (in != null ? in.hashCode() : 0);
		result = 31 * result + (out != null ? out.hashCode() : 0);
		return result;
	}
}
