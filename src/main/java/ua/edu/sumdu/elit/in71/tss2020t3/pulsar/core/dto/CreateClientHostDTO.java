package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientHostDTO {

    @JsonProperty
    private String publicKey;

    @JsonProperty
    private UUID privateKey;

    @JsonProperty
    private String name;
}
