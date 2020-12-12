package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.ClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.UserDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.LoadAverage;

/**
 * A Guice configuration module for support modules
 * */
public class UtilsModule extends AbstractModule {

    @Provides
    Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    @Singleton
    ModelMapper modelMapper() { // model mapper configuration
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
            .typeMap(ClientHost.class, ClientHostDTO.class)
            .addMapping(
                clientHost -> clientHost.getId().getPrivateKey(),
                ClientHostDTO::setPrivateKey
            );
        modelMapper
            .typeMap(User.class, UserDTO.class)
            .addMapping(
                user -> user.getId().getEmail(),
                UserDTO::setEmail
            );
        modelMapper
            .createTypeMap(
                LoadAverage.class,
                Double.class
            )
            .setConverter(
                context -> context.getSource().getLoadAverage()
            );
        modelMapper
            .typeMap(
                ClientHostStatistic.class,
                ClientHostStatisticDTO.class
            )
            .addMapping(
                source -> source.getId().getClientHost().getPublicKey(),
                ClientHostStatisticDTO::setPublicKey
            )
            .addMapping(
                source -> source.getId().getClientLocalTime(),
                ClientHostStatisticDTO::setClientLocalTime
            );
        return modelMapper;
    }
}
