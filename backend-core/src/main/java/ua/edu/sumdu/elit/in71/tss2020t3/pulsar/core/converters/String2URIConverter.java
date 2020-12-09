package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

/**
 * Converts {@link String} to {@link URI}
 * */
public class String2URIConverter implements Function<String, URI> {

    @Override
    public URI apply(String value) {
        try {
            return new URI(value);
        } catch (URISyntaxException e) {
            throw new UncheckedIOException(new IOException(e));
        }
    }
}
