package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 	This class represents client host entity (basically, an agent instance)
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHost implements Serializable {

	private String publicKey;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientHost that = (ClientHost) o;

		return publicKey != null ? publicKey.equals(that.publicKey) : that.publicKey == null;
	}

	@Override
	public int hashCode() {
		return publicKey != null ? publicKey.hashCode() : 0;
	}
}
