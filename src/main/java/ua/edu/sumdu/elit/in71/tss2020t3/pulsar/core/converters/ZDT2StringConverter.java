package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.ZonedDateTime;

/**
 * A converter (in fact - formatter) for {@link ZonedDateTime} values
 * according to the common date-typed user/agent input
 *
 * @see String2ZDTConverter
 * */
public class ZDT2StringConverter extends StdConverter<ZonedDateTime, String> {
    @Override
    public String convert(ZonedDateTime value) {
        return String2ZDTConverter.FORMATTER.format(value);
    }
}
