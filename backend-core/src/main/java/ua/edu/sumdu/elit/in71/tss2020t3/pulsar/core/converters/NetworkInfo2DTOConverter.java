package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import java.util.function.Function;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo;

/**
 * A converter for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo}
 * to its representation-level POJO
 * */
public class NetworkInfo2DTOConverter implements
    Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO> {

    @Override
    public ClientHostStatisticDTO.NetworkInfoDTO apply(NetworkInfo value) {
        return new ClientHostStatisticDTO.NetworkInfoDTO(
            value.getName(),
            value.getIn(),
            value.getOut()
        );
    }
}
