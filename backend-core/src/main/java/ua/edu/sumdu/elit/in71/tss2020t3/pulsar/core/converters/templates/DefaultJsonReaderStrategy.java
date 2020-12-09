package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.templates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;

/**
 * A default implementation for {@link Function}
 * which converts JSON to POJO objects
 *
 * @param <POJO> a type of output POJOs
 * @see          DefaultJsonWriterStrategy
 * */
@AllArgsConstructor
public class DefaultJsonReaderStrategy<POJO>  // todo replace use of other
implements Function<String, POJO> {           // todo similar converters
                                              // todo with this class
    private static final Logger LOGGER = Logger.getLogger(
        DefaultJsonReaderStrategy.class
    );

    private final Class<POJO> pojoClass;

    @Override
    public POJO apply(String json) {
        try {
            return new ObjectMapper().readValue(json, pojoClass);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
