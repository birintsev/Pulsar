package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticDTO;

/**
 * Converts {@link ClientHostStatisticDTO} to {@link String} in JSON format
 * */
public class ServerStatisticDTO2StringConverter
    extends StdConverter<ClientHostStatisticDTO, String> {

    @Override
    public String convert(ClientHostStatisticDTO value) {
        try {
            return new ObjectMapper().writer().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
