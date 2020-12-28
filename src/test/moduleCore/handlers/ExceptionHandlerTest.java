package moduleCore.handlers;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ExceptionHandlerTest {
    @Mock
    Logger LOGGER;
    @InjectMocks
    ExceptionHandler exceptionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        exceptionHandler.handle(null, null);
    }
}
