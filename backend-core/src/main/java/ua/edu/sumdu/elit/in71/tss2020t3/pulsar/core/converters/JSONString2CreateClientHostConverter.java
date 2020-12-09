package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UncheckedIOException;
import java.util.function.Function;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;

public class JSONString2CreateClientHostConverter
    implements Function<String, CreateClientHostDTO> {
    @Override
    public CreateClientHostDTO apply(String value) {
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
