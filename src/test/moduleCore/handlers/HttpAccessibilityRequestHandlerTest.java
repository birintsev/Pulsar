package moduleCore.handlers;

import moduleCore.dto.requests.PingRequest;
import moduleCore.dto.responses.HttpAccessibilityCheckDTO;
import moduleCore.entities.HttpAccessibilityCheck;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.handlers.templates.HandlerAuthenticator;
import moduleCore.handlers.templates.HandlerValidator;
import moduleCore.services.AccessibilityService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class HttpAccessibilityRequestHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    AccessibilityService accessibilityService;
    @Mock
    Function<HttpAccessibilityCheckDTO, String> responseConverter;
    @Mock
    ModelMapper modelMapper;
    @Mock
    HandlerAuthenticator authenticator;
    @InjectMocks
    HttpAccessibilityRequestHandler httpAccessibilityRequestHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleUserRequest() throws Exception {
        when(accessibilityService.checkAccess(any(), anyInt(), any())).thenReturn(new HttpAccessibilityCheck(null, null, new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), null, Integer.valueOf(0), "timeoutChronoUnits", Double.valueOf(0), "description"));

        httpAccessibilityRequestHandler.handleUserRequest(new PingRequest(null, 0), new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), null);
    }

    @Test
    public void testHandle() throws Exception {
        httpAccessibilityRequestHandler.handle(null);
    }
}
