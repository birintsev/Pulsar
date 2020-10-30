package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo;

/**
 * A converter for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo}
 * to its representation-level POJO
 * */
public class DiskInfo2DTOConverter extends
    StdConverter<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO> {

    @Override
    public ClientHostStatisticDTO.DiskInfoDTO convert(DiskInfo value) {
        ClientHostStatisticDTO.DiskInfoDTO dto =
            new ClientHostStatisticDTO.DiskInfoDTO();
        dto.setFree(value.getFree());
        dto.setOrigin(value.getOrigin());
        dto.setTotal(value.getTotal());
        return dto;
    }
}
