package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.net.URI;
import java.util.function.Function;

/**
 * Converts {@link URI} to {@link String}
 * */
public class URI2StringConverter implements Function<URI, String> {

    @Override
    public String apply(URI value) {
        return value.toString();
    }
}
