package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.UncheckedIOException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticDTO;

/**
 * Converts {@link String} to {@link ClientHostStatisticDTO}
 * */
public class String2ServerStatisticDTOConverter
    extends StdConverter<String, ClientHostStatisticDTO> {

    @Override
    public ClientHostStatisticDTO convert(String value) {
        try {
            return new ObjectMapper().readValue(
                value, ClientHostStatisticDTO.class
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
