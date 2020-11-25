package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class been created to represent
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation}
 * entities on representative layer
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String email;

    private String username;

    private int age;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("statuses")
    private Set<UserStatusDTO> userStatuses = new HashSet<>();
}
