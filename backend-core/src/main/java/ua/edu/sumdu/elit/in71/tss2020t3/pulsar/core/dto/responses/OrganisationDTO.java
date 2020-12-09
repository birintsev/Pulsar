package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses;

import java.util.Set;
import java.util.UUID;
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
public class OrganisationDTO {

    private UUID id;

    private String name;

    private UserDTO owner;

    private Set<UserDTO> members;
}
