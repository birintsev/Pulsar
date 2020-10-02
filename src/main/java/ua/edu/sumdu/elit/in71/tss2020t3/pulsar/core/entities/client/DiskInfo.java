package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *	 This class represents a container entity of disk-specific pieces of information received from an agent
 * */
@NoArgsConstructor
@Data
public class DiskInfo implements Serializable {

	private ClientHostStatistic clientHostStatistic;

	private String origin;

	private BigInteger free;

	private BigInteger total;

	@Override
	public String toString() {
		return "DiskInfo{" +
			"clientHostStatistic=" + clientHostStatistic.getID() +
			", origin='" + origin + '\'' +
			", free=" + free +
			", total=" + total +
			'}';
	}
}
