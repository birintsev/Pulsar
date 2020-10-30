package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.CPUInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.DiskInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.LoadAverage;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.MemoryInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.NetworkInfo;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;

/**
 * Converts {@link ClientHostStatisticDTO} to {@link ClientHostStatistic}
 * */
public class ClientHostStatisticFromDTOConverter
    extends StdConverter<ClientHostStatisticDTO, ClientHostStatistic> {

    private final Validator validator;

    private final ClientHostService clientHostService;

    private static final Logger LOGGER = Logger.getLogger(
        ClientHostStatisticFromDTOConverter.class
    );

    /**
     * A default constructor
     *
     * @param clientHostService a service that will be used for determination
     *                          a host to which passed dto relates
     * */
    public ClientHostStatisticFromDTOConverter(
        ClientHostService clientHostService
    ) {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.clientHostService = clientHostService;
    }

    @Override
    public ClientHostStatistic convert(ClientHostStatisticDTO dto) {
        Set<ConstraintViolation<ClientHostStatisticDTO>> constraintViolations =
            validator.validate(dto);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(
                "Converting invalid ClientHostStatisticDTO"
                    + " to ClientHostStatistic."
                    + " The source is below:"
                    + System.lineSeparator()
                    + dto
                    + System.lineSeparator()
                    + "Constraints violations are below:"
                    + System.lineSeparator()
                    + constraintViolations
            );
        }

        ClientHostStatistic clientHostStatistic = new ClientHostStatistic();
        clientHostStatistic.setAgentVersion(dto.getAgentVersion());
        clientHostStatistic.setBootTime(dto.getBootTime());
        clientHostStatistic.setCpuInfoList(
            dto.getCpuInfoList().stream().map(
                CPUInfoDTO -> {
                    CPUInfo cpuinfo = new CPUInfo();
                    cpuinfo.setClientHostStatistic(clientHostStatistic);
                    cpuinfo.setIdle(CPUInfoDTO.getIdle());
                    cpuinfo.setNum(CPUInfoDTO.getNum());
                    cpuinfo.setSystem(CPUInfoDTO.getSystem());
                    cpuinfo.setUser(CPUInfoDTO.getUser());
                    return cpuinfo;
                }
            ).collect(Collectors.toSet())
        );
        clientHostStatistic.setDisksInfo(dto.getDisksInfo().stream().map(
            diskInfoDTO -> {
                DiskInfo diskInfo = new DiskInfo();
                diskInfo.setClientHostStatistic(clientHostStatistic);
                diskInfo.setFree(diskInfoDTO.getFree());
                diskInfo.setOrigin(diskInfoDTO.getOrigin());
                diskInfo.setTotal(diskInfoDTO.getTotal());
                return diskInfo;
            }
        ).collect(Collectors.toSet()));
        clientHostStatistic.setHost(dto.getHost());
        clientHostStatistic.setId(
            new ClientHostStatistic.ClientHostStatisticID(
                clientHostService.getByPublicKey(dto.getPublicKey()),
                dto.getClientLocalTime()
            )
        );
        Set<LoadAverage> loadAverages = new HashSet<>();
        for (int i = 0; i < dto.getLoadAverage().size(); i++) {
            loadAverages.add(
                new LoadAverage(
                    clientHostStatistic, i, dto.getLoadAverage().get(i)
                )
            );
        }
        clientHostStatistic.setLoadAverage(loadAverages);
        clientHostStatistic.setMemoryInfo(
            new MemoryInfo(
                clientHostStatistic,
                dto.getMemoryInfo().getWired(),
                dto.getMemoryInfo().getFree(),
                dto.getMemoryInfo().getActive(),
                dto.getMemoryInfo().getInactive(),
                dto.getMemoryInfo().getTotal()
            )
        );
        clientHostStatistic.setNetworksInfo(
            dto.getNetworksInfo().stream().map(
                e -> new NetworkInfo(
                    clientHostStatistic,
                    e.getName(),
                    e.getIn(),
                    e.getOut()
                )
            ).collect(Collectors.toSet())
        );
        return clientHostStatistic;
    }
}
