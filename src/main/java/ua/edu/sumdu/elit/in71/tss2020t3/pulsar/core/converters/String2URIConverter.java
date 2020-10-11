package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Converts {@link String} to {@link URI}
 * */
public class String2URIConverter extends StdConverter<String, URI> {

    @Override
    public URI convert(String value) {
        try {
            return new URI(value);
        } catch (URISyntaxException e) {
            throw new UncheckedIOException(new IOException(e));
        }
    }
}
