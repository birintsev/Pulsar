package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ZDT2StringConverter;

/**
 * This is a representation-layer POJO for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck}
 * class
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpAccessibilityCheckDTO {

    @JsonProperty("request_id")
    private UUID requestId;

    @JsonProperty("target_url")
    private URL targetUrl;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("checked_when")
    @JsonSerialize(converter = ZDT2StringConverter.class)
    private ZonedDateTime checkedWhen;

    @JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("timeout_cron_units")
    private String timeoutChronoUnits;

    @JsonProperty("response_time")
    private Double responseTime;

    private String description;
}
