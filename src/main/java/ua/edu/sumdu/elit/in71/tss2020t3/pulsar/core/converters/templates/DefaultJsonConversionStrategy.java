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
 * @param <OUT> a type of output POJOs
 * */
@AllArgsConstructor
public class DefaultJsonConversionStrategy<OUT>  // todo replace use of other
implements Function<String, OUT> {               // todo similar converters
                                                 // todo with this class
    private static final Logger LOGGER = Logger.getLogger(
        DefaultJsonConversionStrategy.class
    );

    private final Class<OUT> outClass;

    @Override
    public OUT apply(String json) {
        try {
            return new ObjectMapper().readValue(json, outClass);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
