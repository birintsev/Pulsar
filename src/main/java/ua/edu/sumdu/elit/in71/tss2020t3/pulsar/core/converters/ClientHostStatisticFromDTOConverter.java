package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientHostStatisticFromDTOConverter extends StdConverter<ClientHostStatisticDTO, ClientHostStatistic> {

	private static final Logger LOGGER = Logger.getLogger(ClientHostStatisticFromDTOConverter.class);

	private final StdConverter<ZonedDateTime, java.sql.Date> ZDT2DateConverter;

	public ClientHostStatisticFromDTOConverter() {
		this.ZDT2DateConverter = new ZDT2DateConverter();
	}

	@Override
	public ClientHostStatistic convert(ClientHostStatisticDTO DTO) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ClientHostStatisticDTO>> constraintViolations = validator.validate(DTO);
		if (!constraintViolations.isEmpty()) {
			LOGGER.warn(
				"Converting invalid ClientHostStatisticDTO to ClientHostStatistic." +
					" The source is below:" + System.lineSeparator() +
					DTO + System.lineSeparator() +
					"Constraints violations are below:" + System.lineSeparator() +
					constraintViolations
			);
		}

		ClientHostStatistic clientHostStatistic = new ClientHostStatistic();
		clientHostStatistic.setAgentVersion(DTO.getAgentVersion());
		clientHostStatistic.setBootTime(DTO.getBootTime());
		clientHostStatistic.setCPUInfoList(
			DTO.getCPUInfoList().stream().map(
				CPUInfoDTO -> {
					CPUInfo CPUInfo = new CPUInfo();
					CPUInfo.setClientHostStatistic(clientHostStatistic);
					CPUInfo.setIdle(CPUInfoDTO.getIdle());
					CPUInfo.setNum(CPUInfoDTO.getNum());
					CPUInfo.setSystem(CPUInfoDTO.getSystem());
					CPUInfo.setUser(CPUInfoDTO.getUser());
					return CPUInfo;
				}
			).collect(Collectors.toSet())
		);
		clientHostStatistic.setDisksInfo(DTO.getDisksInfo().stream().map(
			diskInfoDTO -> {
				DiskInfo diskInfo = new DiskInfo();
				diskInfo.setClientHostStatistic(clientHostStatistic);
				diskInfo.setFree(diskInfoDTO.getFree());
				diskInfo.setOrigin(diskInfoDTO.getOrigin());
				diskInfo.setTotal(diskInfoDTO.getTotal());
				return diskInfo;
			}
		).collect(Collectors.toSet()));
		try {
			clientHostStatistic.setHost(new URI(DTO.getHost()));
		} catch (URISyntaxException e) {
			String errMsg = "Error during host converting from String to URI:" + System.lineSeparator() + e;
			LOGGER.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}
		clientHostStatistic.setID(
			new ClientHostStatistic.ClientHostStatisticID(
				new ClientHost(DTO.getPublicKey()), DTO.getClientLocalTime()
			)
		);
		Set<LoadAverage> loadAverages = new HashSet<>();
		for (int i = 0; i < DTO.getLoadAverage().size(); i++) {
			loadAverages.add(new LoadAverage(clientHostStatistic, i, DTO.getLoadAverage().get(i)));
		}
		clientHostStatistic.setLoadAverage(loadAverages);
		clientHostStatistic.setMemoryInfo(
			new MemoryInfo(
				clientHostStatistic,
				DTO.getMemoryInfo().getWired(),
				DTO.getMemoryInfo().getFree(),
				DTO.getMemoryInfo().getActive(),
				DTO.getMemoryInfo().getInactive(),
				DTO.getMemoryInfo().getTotal()
			)
		);
		clientHostStatistic.setNetworksInfo(
			DTO.getNetworksInfo().stream().map(
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
