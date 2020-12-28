package moduleCore.handlers;

import moduleCore.handlers.templates.HandlerAuthenticator;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UpdateUserStatusHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    HandlerAuthenticator authenticator;
    @InjectMocks
    UpdateUserStatusHandler updateUserStatusHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        updateUserStatusHandler.handle(null);
    }
}
