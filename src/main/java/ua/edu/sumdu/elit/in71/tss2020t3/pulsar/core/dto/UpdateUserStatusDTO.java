package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserStatusDTO {

    @NotNull(message = "Status is required")
    @JsonProperty(required = true)
    private String status;

    @NotNull(message = "Action is required")
    @JsonProperty(required = true)
    private Action action;

    public enum Action {
        ADD, REMOVE
    }
}
