package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.Duration;
import java.util.function.Function;

public class Duration2StringWriter
extends StdConverter<Duration, String>
implements Function<Duration, String> {

    @Override
    public String apply(Duration duration) {
        return duration.toString();
    }

    @Override
    public String convert(Duration value) {
        return apply(value);
    }
}
