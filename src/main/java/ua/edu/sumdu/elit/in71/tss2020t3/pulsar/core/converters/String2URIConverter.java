package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;

public class String2URIConverter extends StdConverter<String, URI> {

	private static final Logger LOGGER = Logger.getLogger(String2URIConverter.class);

	@Override
	public URI convert(String value) {
		try {
			return new URI(value);
		} catch (URISyntaxException e) {
			String errMsg = "Error during converting URL from the string below:" + System.lineSeparator() + value;
			LOGGER.error(errMsg, e);
			throw new UncheckedIOException(new IOException(e));
		}
	}
}
