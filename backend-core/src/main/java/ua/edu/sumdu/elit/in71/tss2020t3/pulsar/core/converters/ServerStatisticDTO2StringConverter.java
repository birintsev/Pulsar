package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;

/**
 * Converts {@link ClientHostStatisticDTO} to {@link String} in JSON format
 * */
public class ServerStatisticDTO2StringConverter
    implements Function<ClientHostStatisticDTO, String> {

    @Override
    public String apply(ClientHostStatisticDTO value) {
        try {
            return new ObjectMapper().writer().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
