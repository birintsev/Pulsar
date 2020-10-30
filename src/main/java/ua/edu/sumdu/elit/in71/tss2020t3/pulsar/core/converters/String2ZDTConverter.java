package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A converter for date-typed parameters received from a user or an agent
 * */
public class String2ZDTConverter extends StdConverter<String, ZonedDateTime> {

    /**
     * A date-time formatter that should be used for parsing date parameters
     * received from an agent or a user (where it's possible)
     * */
    public static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    @Override
    public ZonedDateTime convert(String value) {
        return ZonedDateTime.from(FORMATTER.parse(value));
    }
}
