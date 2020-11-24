package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a POJO that represents
 * a {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User} request
 * to join
 * an {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation}
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinOrganisationRequest {

    /**
     * An {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation}
     * identifier a user request to join
     * */
    private UUID organisationId;
}
