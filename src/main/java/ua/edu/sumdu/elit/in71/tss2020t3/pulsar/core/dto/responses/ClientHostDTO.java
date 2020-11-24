package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHostDTO {

    @JsonProperty(value = "private_key")
    private String privateKey;

    @JsonProperty(value = "public_key")
    private String publicKey;

    @JsonProperty
    private String name;
}
