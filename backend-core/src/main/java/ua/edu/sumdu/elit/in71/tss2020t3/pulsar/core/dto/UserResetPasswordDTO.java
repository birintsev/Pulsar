package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPasswordDTO {

    @JsonProperty("reset_key")
    private UUID resetKey;

    @JsonProperty
    private String password;
}
