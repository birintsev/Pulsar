package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.javalin.http.Context;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.function.Function;
import javax.validation.Validator;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.CPUInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatistic2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatisticFromDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.DiskInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JSONString2CreateClientHostConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.MemoryInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.NetworkInfo2DTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.String2ZDTConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.UserRegistrationDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.templates.DefaultJsonReaderStrategy;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.templates.DefaultJsonWriterStrategy;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateClientHostDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.CreateOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.JoinOrganisationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.SubscribeToClientHostRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UpdateUserStatusDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRegistrationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserRequestToResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.UserResetPasswordDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker.CreateHttpAccessibilityCheckConfigurationRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests.activetracker.GetHttpAccessibilityCheckResultsRequest;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.UserDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.activetracker.HttpAccessibilityCheckResultDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.MemoryInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers.activetracker.GetHttpAccessibilityCheckResultsRequestHandler;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;

// todo rename all toString-converters to 'writers'
// todo rename all fromString-converters to 'readers'
/**
 * A Guice configuration module for components that provides support of
 * converting objects (e.g {@link java.util.function.Function},
 * {@link com.fasterxml.jackson.databind.util.Converter} etc)
 * */
public class ConversionServicesModule extends AbstractModule {

    @Provides
    Function<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> cpu2DTOConverter() {
        return new CPUInfo2DTOConverter();
    }

    @Provides
    Function<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
    memory2DTOConverter() {
        return new MemoryInfo2DTOConverter();
    }

    @Provides
    Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
    network2DTOConverter() {
        return new NetworkInfo2DTOConverter();
    }

    @Provides
    Function<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
    disk2DTOConverter() {
        return new DiskInfo2DTOConverter();
    }

    @Provides
    Function<ClientHostStatistic, ClientHostStatisticDTO>
    statistic2DTOConverter(
        Function<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> cpu2DTOConverter,
        Function<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
            memory2DTOConverter,
        Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
            network2DTOConverter,
        Function<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
            disk2DTOConverter
    ) {
        return new ClientHostStatistic2DTOConverter(
            cpu2DTOConverter,
            disk2DTOConverter,
            network2DTOConverter,
            memory2DTOConverter
        );
    }

    @Provides
    Function<String, CreateOrganisationRequest>
    createOrganisationRequestBodyConverter() {
        return body -> {
            try {
                return new ObjectMapper().readValue(
                    body, CreateOrganisationRequest.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, UserResetPasswordDTO>
    userResetPasswordDTOConverter() {
        return string -> {
            try {
                return new ObjectMapper().readValue(
                    string, UserResetPasswordDTO.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, UserRequestToResetPasswordDTO>
    userRequestToResetPasswordDTOConverter() {
        return string -> {
            try {
                return new ObjectMapper().readValue(
                    string, UserRequestToResetPasswordDTO.class
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<String, SubscribeToClientHostRequest>
    subscribeToClientHostRequestConverter() {
        return s -> {
            try {
                return new ObjectMapper()
                    .readValue(s, SubscribeToClientHostRequest.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Provides
    Function<ClientHostStatisticDTO, ClientHostStatistic>
    clientHostStatisticConverter(
        ClientHostService clientHostService,
        Validator validator
    ) {
        return new ClientHostStatisticFromDTOConverter(
            clientHostService,
            validator
        );
    }

    @Provides
    Function<String, CreateClientHostDTO> createClientHostDTOConverter() {
        return new JSONString2CreateClientHostConverter();
    }

    @Provides
    Function<UserDTO, String> userDTO2StringConversionStrategy() {
        return new DefaultJsonWriterStrategy<>(UserDTO.class);
    }

    @Provides
    Function<Object, String> responseConversionStrategy() {
        return new DefaultJsonWriterStrategy<>(Object.class);
    }

    @Provides
    Function<Context, JoinOrganisationRequest>
    joinOrganisationRequestConverter(
        Function<String, JoinOrganisationRequest> bodyConverter
    ) {
        return context -> bodyConverter.apply(context.body());
    }

    @Provides
    Function<String, JoinOrganisationRequest>
    joinOrganisationRequestStringConverter() {
        return new DefaultJsonReaderStrategy<>(JoinOrganisationRequest.class);
    }

    @Provides
    Function<Context, UpdateUserStatusDTO>
    updateUserStatusDTOConverter(
        Function<String, UpdateUserStatusDTO> bodyConverter
    ) {
        return context -> bodyConverter.apply(context.body());
    }

    @Provides
    Function<String, UpdateUserStatusDTO>
    updateUserStatusDTOStringConverter() {
        return new DefaultJsonReaderStrategy<>(UpdateUserStatusDTO.class);
    }

    @Provides
    Function<HttpAccessibilityCheckResultDTO, String>
    httpAccessibilityCheckDTOWriter() {
        return new DefaultJsonWriterStrategy<>(
            HttpAccessibilityCheckResultDTO.class
        );
    }

    @Provides
    Function<String, UserRegistrationDTO> userRegistrationDTOReader() {
        return new DefaultJsonReaderStrategy<>(UserRegistrationDTO.class);
    }

    @Provides
    Function<UserRegistrationDTO, User> userRegistrationDTOConverter() {
        return new UserRegistrationDTOConverter();
    }

    @Provides
    Function<String, CreateHttpAccessibilityCheckConfigurationRequest>
    createHttpAccessibilityCheckConfigurationRequestReader() {
        return new DefaultJsonReaderStrategy<>(
            CreateHttpAccessibilityCheckConfigurationRequest.class
        );
    }

    @Provides
    Function<Context, CreateHttpAccessibilityCheckConfigurationRequest>
    createHttpAccessibilityCheckConfigurationRequestConverter(
        Function<String, CreateHttpAccessibilityCheckConfigurationRequest>
            bodyReader
    ) {
        return context -> bodyReader.apply(context.body());
    }

    @Provides
    Function<Context, GetHttpAccessibilityCheckResultsRequest>
    getActiveChecksRequestExtractor(
        Function<String, ZonedDateTime> zonedDateTimeReader
    ) {
        return
            new GetHttpAccessibilityCheckResultsRequestHandler
                .RequestConverter(
                    zonedDateTimeReader
                );
    }

    @Provides
    Function<String, ZonedDateTime> zonedDateTimeReader() {
        return new String2ZDTConverter();
    }

    @Provides
    Function<Duration, String> durationStringWriter() {
        return Duration::toString;
    }
}
