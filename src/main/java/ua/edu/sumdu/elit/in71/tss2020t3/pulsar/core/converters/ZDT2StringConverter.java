package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.time.ZonedDateTime;
import java.util.function.Function;

/**
 * A converter (in fact - formatter) for {@link ZonedDateTime} values
 * according to the common date-typed user/agent input
 *
 * @see String2ZDTConverter
 * */
public class ZDT2StringConverter
    extends StdConverter<ZonedDateTime, String>
    implements Function<ZonedDateTime, String> {

    @Override
    public String apply(ZonedDateTime value) {
        return String2ZDTConverter.FORMATTER.format(value);
    }

    @Override
    public String convert(ZonedDateTime value) {
        return apply(value);
    }
}
