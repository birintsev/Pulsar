package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Duration;

public class JacksonDurationReader extends StdConverter<String, Duration> {

    @Override
    public Duration convert(String value) {
        return Duration.parse(value);
    }
}
