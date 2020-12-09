package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.net.URL;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JacksonDurationReader;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHttpAccessibilityCheckConfigurationRequest {

    @JsonProperty("target_url")
    private URL targetUrl;

    /**
     * ISO-8601 second-based representation
     * */
    @JsonProperty("check_interval")
    @JsonDeserialize(converter = JacksonDurationReader.class)
    private Duration interval;

    /**
     * ISO-8601 second-based representation
     * */
    @JsonProperty("response_timeout")
    @JsonDeserialize(converter = JacksonDurationReader.class)
    private Duration responseTimeout;
}
