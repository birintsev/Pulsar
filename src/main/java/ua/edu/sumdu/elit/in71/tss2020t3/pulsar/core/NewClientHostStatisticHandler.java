package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.Converter;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatisticFromDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.String2ServerStatisticDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.net.HttpURLConnection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents a controller for inputs (statistic to save) from client hosts agents.
 *
 * @see			JavalinApplication
 * @see			ClientHostStatistic
 * */
public class NewClientHostStatisticHandler implements Handler {

	private final Converter<String, ClientHostStatisticDTO> deserealizator;

	private final Validator validator;

	private final ClientHostStatisticService clientHostStatisticService;

	private final Converter<ClientHostStatisticDTO, ClientHostStatistic> DTOConverter;

	private static final Logger LOGGER = Logger.getLogger(NewClientHostStatisticHandler.class);

	/**
	 * @param			sessionFactory	A session facotry that will be used during input handling to persist statistic
	 * */
	public NewClientHostStatisticHandler(SessionFactory sessionFactory) {
		this.deserealizator = new String2ServerStatisticDTOConverter();
		this.validator = Validation.buildDefaultValidatorFactory().getValidator();
		this.clientHostStatisticService = new ClientHostStatisticServiceImpl(sessionFactory);
		this.DTOConverter = new ClientHostStatisticFromDTOConverter();
	}

	@Override
	public void handle(@NotNull Context ctx) {
		try {
			ClientHostStatisticDTO DTO = deserealizator.convert(ctx.body());
			Set<ConstraintViolation<ClientHostStatisticDTO>> violations = validator.validate(DTO);
			if (!violations.isEmpty()) {
				LOGGER.error(
					"Invalid request. The context is below: " +
						System.lineSeparator() +
						ctx +
						"Constraints violations are below:" +
						System.lineSeparator() +
						violations
				);
				ctx.status(HttpURLConnection.HTTP_BAD_REQUEST);
				ctx.result(createBadResponse(violations));
			} else {
				clientHostStatisticService.save(DTOConverter.convert(DTO));
				LOGGER.trace("Incoming client host statistic has been successfully saved");
			}
		} catch (Throwable t) {
			LOGGER.error(t);
			ctx.status(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class BadResponseTemplate {
		private Set<String> errors;
	}

	private String createBadResponse(Set<ConstraintViolation<ClientHostStatisticDTO>> violations) {
		BadResponseTemplate badResponseTemplate = new BadResponseTemplate(
			violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet())
		);
		try {
			return new ObjectMapper().writeValueAsString(badResponseTemplate);
		} catch (JsonProcessingException e) {
			String errMsg = "Unchecked exception during marshalling " + badResponseTemplate + " to JSON";
			LOGGER.error(errMsg);
			throw new RuntimeException(errMsg, e);
		}
	}
}
