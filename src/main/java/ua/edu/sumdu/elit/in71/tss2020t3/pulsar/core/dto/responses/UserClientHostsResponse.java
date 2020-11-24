package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class been created to represent a response on user request
 * to get all the
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost#getOwner() owned}
 * and
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.SubscribeClientHostHandler subscribed}
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost client hosts}.
 * Shared client hosts of members of all
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation organisations}
 * to which the
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User user}
 * belongs will be contained as well.
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientHostsResponse {

    @JsonProperty("owned_client_hosts")
    private List<ClientHostDTO> ownedClientHosts;

    @JsonProperty("subscribed_client_hosts")
    private List<ClientHostDTO> subscribedClientHosts;

    @JsonProperty("organisations_client_hosts")
    private List<OrganisationClientsHosts> organisationClientHosts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrganisationClientsHosts {

        @JsonProperty("organisation_id")
        private UUID organisationId;

        @JsonProperty("client_hosts")
        private List<ClientHostDTO> clientHosts;
    }
}
