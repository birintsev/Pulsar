package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * Converts {@link BigInteger} to {@link String}
 * */
public class BigInteger2StringConverter
    extends StdConverter<BigInteger, String>
    implements Function<BigInteger, String> {

    @Override
    public String apply(BigInteger value) {
        return value.toString();
    }

    @Override
    public String convert(BigInteger value) {
        return apply(value);
    }
}
