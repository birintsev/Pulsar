package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.math.BigInteger;
import java.util.function.Function;

/**
 * Converts {@link String} to {@link BigInteger}
 * */
public class String2BigIntegerConverter
    extends StdConverter<String, BigInteger>
    implements Function<String, BigInteger> {

    @Override
    public BigInteger apply(String value) {
        return new BigInteger(value);
    }

    @Override
    public BigInteger convert(String value) {
        return apply(value);
    }
}
