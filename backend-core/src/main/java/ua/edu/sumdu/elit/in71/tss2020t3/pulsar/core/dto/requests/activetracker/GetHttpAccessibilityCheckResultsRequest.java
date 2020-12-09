package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.net.URL;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.String2ZDTConverter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHttpAccessibilityCheckResultsRequest {

    @JsonProperty("target_url")
    private URL targetUrl;

    @JsonProperty("start_date")
    @JsonDeserialize(converter = String2ZDTConverter.class)
    private ZonedDateTime startDate;

    @JsonProperty("end_date")
    @JsonDeserialize(converter = String2ZDTConverter.class)
    private ZonedDateTime endDate;
}
