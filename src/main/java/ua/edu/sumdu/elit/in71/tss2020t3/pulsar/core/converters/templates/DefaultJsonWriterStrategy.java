package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

/**
 * A default implementation for {@link Function}
 * which converts POJO objects to a JSON string
 *
 * @param <POJO> a type of POJOs for converting
 * @see          DefaultJsonReaderStrategy
 * */
@AllArgsConstructor
public class DefaultJsonWriterStrategy<POJO> implements Function<POJO, String> {

    private static final Logger LOGGER = Logger.getLogger(
        DefaultJsonWriterStrategy.class
    );

    private final Class<POJO> pojoClass;

    @Override
    public String apply(POJO pojo) {
        try {
            return new ObjectMapper().writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
