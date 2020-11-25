package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.responses.OrganisationDTO;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;

/**
 * This handler provides a possibility to get information
 * about all the
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation organisations}.
 * Support of such features as filtering, ordering and pagination will be added
 * in the future (if such need arises).
 * */
@AllArgsConstructor
public class GetOrganisationsHandler implements Handler {

    private static final Logger LOGGER = Logger.getLogger(
        GetOrganisationsHandler.class
    );

    private final OrganisationService organisationService;

    private final ModelMapper modelMapper;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        ctx.result(
            new ObjectMapper().writeValueAsString(
                modelMapper.map(
                    organisationService.getAll(),
                    new TypeToken<Set<OrganisationDTO>>() {
                    }.getType()
                )
            )
        );
    }
}
