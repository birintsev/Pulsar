package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javax.validation.Validation;
import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;

/**
 * A Guice configuration module for support modules
 * */
public class UtilsModule extends AbstractModule {

    @Provides
    Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    ModelMapper modelMapper() { // model mapper configuration
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
            .typeMap(ClientHost.class, ClientHostDTO.class)
            .addMapping(
                source -> source.getId().getPrivateKey(),
                ClientHostDTO::setPrivateKey
            );
        return modelMapper;
    }
}
