package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo;

/**
 * A converter for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo}
 * to its representation-level POJO
 * */
public class CPUInfo2DTOConverter
    extends StdConverter<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> {
    @Override
    public ClientHostStatisticDTO.CPUInfoDTO convert(CPUInfo value) {
        ClientHostStatisticDTO.CPUInfoDTO dto =
            new ClientHostStatisticDTO.CPUInfoDTO();
        dto.setIdle(value.getIdle());
        dto.setNum(value.getNum());
        dto.setSystem(value.getSystem());
        dto.setUser(value.getUser());
        return dto;
    }
}
