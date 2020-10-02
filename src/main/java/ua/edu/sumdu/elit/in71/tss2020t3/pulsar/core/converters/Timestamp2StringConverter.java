package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Timestamp2StringConverter extends StdConverter<Timestamp, String> {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

	@Override
	public String convert(Timestamp value) {
		return FORMATTER.format(value.toInstant());
	}
}
