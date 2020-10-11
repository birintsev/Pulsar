package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Converts {@link String} to {@link Timestamp}
 * using {@link Timestamp2StringConverter#FORMATTER}
 *
 * @see Timestamp2StringConverter
 * */
public class String2TimestampConverter extends StdConverter<String, Timestamp> {

    @Override
    public Timestamp convert(String value) {
        return Timestamp.from(
            Instant.from(
                Timestamp2StringConverter.FORMATTER.parse(value)
            )
        );
    }
}
