package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.UncheckedIOException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;

public class JSONString2CreateClientHostConverter
    extends StdConverter<String, CreateClientHostDTO> {
    @Override
    public CreateClientHostDTO convert(String value) {
        try {
            return new ObjectMapper().readValue(
                value,
                CreateClientHostDTO.class
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(
                "Can not convert string to " + CreateClientHostDTO.class,
                e
            );
        }
    }
}
