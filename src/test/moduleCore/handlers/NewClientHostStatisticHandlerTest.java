package moduleCore.handlers;

import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.client.ClientHostStatistic;
import moduleCore.services.ClientHostStatisticService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class NewClientHostStatisticHandlerTest {
    @Mock
    Function<String, ClientHostStatisticDTO> deserializer;
    @Mock
    Validator validator;
    @Mock
    ClientHostStatisticService clientHostStatisticService;
    @Mock
    Function<ClientHostStatisticDTO, ClientHostStatistic> dtoConverter;
    @Mock
    Logger LOGGER;
    @InjectMocks
    NewClientHostStatisticHandler newClientHostStatisticHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        newClientHostStatisticHandler.handle(null);
    }
}
