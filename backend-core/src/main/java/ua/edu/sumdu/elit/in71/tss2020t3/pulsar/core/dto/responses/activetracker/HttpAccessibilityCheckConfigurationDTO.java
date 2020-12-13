package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.activetracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.net.URL;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.Duration2StringWriter;

/**
 * This is a representation-layer POJO for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker.HttpAccessibilityCheckConfiguration}
 * class
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpAccessibilityCheckConfigurationDTO {

    @JsonProperty("target_url")
    private URL targetUrl;

    /**
     * ISO-8601 second-based representation
     * */
    @JsonProperty("response_timeout")
    @JsonSerialize(converter = Duration2StringWriter.class)
    private Duration responseTimeout;
}
