package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.LoadAverage;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.MemoryInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo;

/**
 * A converter for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic}
 * to its representation-level POJO
 * */
public class ClientHostStatistic2DTOConverter extends
    StdConverter<ClientHostStatistic, ClientHostStatisticDTO> {

    private final Converter<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO>
        cpuInfoConverter;

    private final Converter<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
        diskInfoConverter;

    private final Converter<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
        networkInfoConverter;

    private final Converter<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
        memoryInfoConverter;

    /**
     * A default constructor for dependency injection
     *
     * @param cpuInfoConverter     a converter for statistic-of-CPU container
     *                             objects from their business-level POJOs
     *                             to representation-level POJOs
     * @param diskInfoConverter    a converter for statistic-of-disk container
     *                             objects from their business-level POJOs
     *                             to representation-level POJOs
     * @param networkInfoConverter a converter for network-related statistic
     *                             container objects from their business-level
     *                             POJOs to representation-level POJOs
     * @param memoryInfoConverter  a converter for memory statistic container
     *                             objects from their business-level POJOs
     *                             to representation-level POJOs
     * */
    public ClientHostStatistic2DTOConverter(
        Converter<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO>
            cpuInfoConverter,
        Converter<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO>
            diskInfoConverter,
        Converter<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO>
            networkInfoConverter,
        Converter<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO>
            memoryInfoConverter
    ) {
        this.cpuInfoConverter = cpuInfoConverter;
        this.diskInfoConverter = diskInfoConverter;
        this.networkInfoConverter = networkInfoConverter;
        this.memoryInfoConverter = memoryInfoConverter;
    }

    @Override
    public ClientHostStatisticDTO convert(ClientHostStatistic value) {
        ClientHostStatisticDTO dto = new ClientHostStatisticDTO();
        dto.setAgentVersion(value.getAgentVersion());
        dto.setBootTime(value.getBootTime());
        dto.setClientLocalTime(value.getId().getClientLocalTime());
        dto.setBootTime(value.getBootTime());
        dto.setHost(value.getHost());
        dto.setCpuInfoList(
            convert(value.getCpuInfoList(), cpuInfoConverter)
        );
        dto.setDisksInfo(
            convert(value.getDisksInfo(), diskInfoConverter)
        );
        dto.setNetworksInfo(
            convert(value.getNetworksInfo(), networkInfoConverter)
        );
        dto.setLoadAverage(
            value.getLoadAverage()
                .stream()
                .sorted(Comparator.comparingInt(LoadAverage::getOrder))
                .map(LoadAverage::getLoadAverage)
                .collect(Collectors.toList()));
        dto.setMemoryInfo(
            memoryInfoConverter.convert(value.getMemoryInfo())
        );
        return dto;
    }

    private <SOURCE, DESTINATION> List<DESTINATION> convert(
        Collection<SOURCE> source, Converter<SOURCE, DESTINATION> converter
    ) {
        return source
            .stream()
            .map(converter::convert)
            .collect(Collectors.toList());
    }
}
