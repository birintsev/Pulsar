package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.math.BigInteger;

public class BigInteger2StringConverter extends StdConverter<BigInteger, String> {

	@Override
	public String convert(BigInteger value) {
		return value.toString();
	}
}
