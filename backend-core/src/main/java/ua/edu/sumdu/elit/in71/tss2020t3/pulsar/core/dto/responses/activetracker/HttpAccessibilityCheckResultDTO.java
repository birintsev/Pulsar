package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.activetracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Duration;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.Duration2StringWriter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ZDT2StringConverter;

/**
 * This is a representation-layer POJO for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckResult}
 * class
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpAccessibilityCheckResultDTO {

    private HttpAccessibilityCheckConfigurationDTO configuration;

    @JsonProperty("checked_when")
    @JsonSerialize(converter = ZDT2StringConverter.class)
    private ZonedDateTime checkedWhen;

    @JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("response_time")
    @JsonSerialize(converter = Duration2StringWriter.class)
    private Duration responseTime;

    private String description;
}
