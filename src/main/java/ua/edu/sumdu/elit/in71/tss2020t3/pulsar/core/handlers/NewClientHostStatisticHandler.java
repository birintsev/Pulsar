package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.databind.util.Converter;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ClientHostStatisticServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.ClientHostStatisticFromDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.converters.JSONString2ServerStatisticDTOConverter;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.ClientHostStatisticDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHostStatistic;

/**
 * This class represents a controller for inputs
 * (i.e portions of statistic to save) from client hosts agents
 *
 * @see ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.JavalinApplication
 * @see ClientHostStatistic
 * */
public class NewClientHostStatisticHandler implements Handler {

    private final Converter<String, ClientHostStatisticDTO> deserializer;

    private final Validator validator;

    private final ClientHostStatisticService clientHostStatisticService;

    private final Converter<ClientHostStatisticDTO, ClientHostStatistic>
        dtoConverter;

    private static final Logger LOGGER = Logger.getLogger(
        NewClientHostStatisticHandler.class
    );

    /**
     * @param sessionFactory A session factory that will be used
     *                       during input handling to persist statistic
     * */
    public NewClientHostStatisticHandler(SessionFactory sessionFactory) {
        this.deserializer = new JSONString2ServerStatisticDTOConverter();
        this.validator =
            Validation.buildDefaultValidatorFactory().getValidator();
        this.clientHostStatisticService = new ClientHostStatisticServiceImpl(
            sessionFactory
        );
        this.dtoConverter = new ClientHostStatisticFromDTOConverter();
    }

    /**
     * Saves passed statistic to the database
     *
     * @param ctx an input {@link Context} from a client host agent
     * */
    @Override
    public void handle(@NotNull Context ctx) {
        validate(ctx);
        clientHostStatisticService.save(
            dtoConverter.convert(
                deserializer.convert(ctx.body())
            )
        );
        LOGGER.trace(
            "Incoming client host statistic from "
                + ctx.req.getRemoteAddr() + " has been successfully saved"
        );
    }

    /**
     * Validates a request ctx
     *
     * @param     ctx                a {@link Context} from a client agent
     * @exception BadRequestResponse if the request body does not represent
     *                               a valid request from a client agent
     * @see                          ClientHostStatistic
     * */
    private void validate(Context ctx) {
        BadRequestResponse badResponse;
        ClientHostStatisticDTO dto;
        try {
            dto = deserializer.convert(ctx.body());
        } catch (Exception e) {
            LOGGER.error(
                "Error during unmarshalling request body: " + ctx.body()
                    + " to " + ClientHostStatisticDTO.class
            );
            throw new BadRequestResponse(
                "Invalid request."
                    + " Please, check the request body has all the fields"
            );
        }
        Set<ConstraintViolation<ClientHostStatisticDTO>> violations =
            validator.validate(dto);
        if (!violations.isEmpty()) {
            LOGGER.error(
                "Invalid request from a client agent. The context is: "
                    + ctx + "Constraints violations: " + violations
            );
            badResponse = new BadRequestResponse();
            violations.forEach(constraintViolation ->
                badResponse.getDetails().put(
                String.valueOf(constraintViolation.getInvalidValue()),
                constraintViolation.getMessage()
            ));
            throw badResponse;
        }
    }
}
