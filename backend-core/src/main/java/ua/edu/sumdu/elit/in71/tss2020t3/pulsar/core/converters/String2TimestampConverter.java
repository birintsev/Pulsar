package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.function.Function;

/**
 * Converts {@link String} to {@link Timestamp}
 * using {@link Timestamp2StringConverter#FORMATTER}
 *
 * @see Timestamp2StringConverter
 * */
public class String2TimestampConverter implements Function<String, Timestamp> {

    @Override
    public Timestamp apply(String value) {
        return Timestamp.from(
            Instant.from(
                Timestamp2StringConverter.FORMATTER.parse(value)
            )
        );
    }
}
