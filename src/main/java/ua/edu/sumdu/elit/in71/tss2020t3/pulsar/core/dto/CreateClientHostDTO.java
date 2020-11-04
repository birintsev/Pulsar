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

    @JsonProperty(required = true)
    private String publicKey;

    @JsonProperty(required = true)
    private UUID privateKey;

    @JsonProperty(required = true)
    private String name;
}
