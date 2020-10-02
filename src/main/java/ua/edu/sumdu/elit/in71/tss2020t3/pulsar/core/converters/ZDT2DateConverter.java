package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;

public class ZDT2DateConverter extends StdConverter<ZonedDateTime, java.sql.Date> {
	@Override
	public Date convert(ZonedDateTime value) {
		return new Date(Instant.from(value).getEpochSecond());
	}
}
