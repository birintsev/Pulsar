package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents
 * a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User} request
 * to create
 * an {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation}
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganisationRequest {

    @JsonProperty(required = true)
    @NotBlank(message = "Organisation name must be set")
    private String name;
}
