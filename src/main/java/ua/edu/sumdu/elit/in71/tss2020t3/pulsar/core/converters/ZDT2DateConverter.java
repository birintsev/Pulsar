package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * Converts {@link ZonedDateTime} to {@link java.sql.Date}
 * */
public class ZDT2DateConverter
    implements Function<ZonedDateTime, Date> {

    @Override
    public Date apply(ZonedDateTime value) {
        return new Date(Instant.from(value).getEpochSecond());
    }
}
