package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.io.UncheckedIOException;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.UserRegistrationDTO;

/**
 * Converts {@link String} to {@link UserRegistrationDTO}
 * */
public class JSONString2UserDTOConverter
    extends StdConverter<String, UserRegistrationDTO> {

    private static final Logger LOGGER = Logger.getLogger(
        JSONString2UserDTOConverter.class
    );

    /**
     * Unmarshalls a {@link UserRegistrationDTO} from its JSON representation
     *
     * @param   value   a JSON representation of {@link UserRegistrationDTO}
     * @return          unmarshalled {@link UserRegistrationDTO}
     *                  from passed {@link String}
     * */
    @Override
    public UserRegistrationDTO convert(String value) {
        try {
            return new ObjectMapper().readValue(
                value,
                UserRegistrationDTO.class
            );
        } catch (JsonProcessingException e) {
            LOGGER.error(
                "Can not parse a "
                    + UserRegistrationDTO.class
                    + " from the string: "
                    + value
            );
            throw new UncheckedIOException(e);
        }
    }
}
