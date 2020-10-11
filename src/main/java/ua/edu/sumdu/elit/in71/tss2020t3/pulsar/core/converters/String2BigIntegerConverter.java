package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigInteger;

/**
 * Converts {@link String} to {@link BigInteger}
 * */
public class String2BigIntegerConverter
    extends StdConverter<String, BigInteger> {

    @Override
    public BigInteger convert(String value) {
        return new BigInteger(value);
    }
}
