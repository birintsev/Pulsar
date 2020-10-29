package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client;

import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * This class represents client host entity (basically, an agent instance)
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHost implements Serializable {

    private ID id;

    private String publicKey;

    private User owner;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientHost that = (ClientHost) o;

        return Objects.equals(publicKey, that.publicKey);
    }

    @Override
    public int hashCode() {
        return publicKey != null ? publicKey.hashCode() : 0;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ID implements Serializable {
        private UUID privateKey;
    }
}
