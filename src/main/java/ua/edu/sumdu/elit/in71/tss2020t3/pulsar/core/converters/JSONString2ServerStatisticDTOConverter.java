package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.UncheckedIOException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticDTO;

/**
 * Converts {@link String} to {@link ClientHostStatisticDTO}
 * */
public class JSONString2ServerStatisticDTOConverter
    extends StdConverter<String, ClientHostStatisticDTO> {

    /**
     * Unmarshalls a {@link ClientHostStatisticDTO} from its JSON representation
     *
     * @param   value   a JSON representation of {@link ClientHostStatisticDTO}
     * @return          unmarshalled {@link ClientHostStatisticDTO}
     *                  from passed {@link String}
     * */
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
