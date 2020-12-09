package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Converts {@link Timestamp} to {@link String}
 * using {@link Timestamp2StringConverter#FORMATTER}
 *
 * @see String2TimestampConverter
 * */
public class Timestamp2StringConverter implements Function<Timestamp, String> {

    public static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    @Override
    public String apply(Timestamp value) {
        return FORMATTER.format(value.toInstant());
    }
}
