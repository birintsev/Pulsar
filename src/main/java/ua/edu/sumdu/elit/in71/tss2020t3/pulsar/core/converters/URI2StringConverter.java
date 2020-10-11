package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.net.URI;

/**
 * Converts {@link URI} to {@link String}
 * */
public class URI2StringConverter extends StdConverter<URI, String> {

    @Override
    public String convert(URI value) {
        return value.toString();
    }
}
