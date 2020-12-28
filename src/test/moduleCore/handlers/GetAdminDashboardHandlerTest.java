package moduleCore.handlers;

import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.services.AdminDashboardService;
import moduleCore.services.security.AuthenticationStrategy;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class GetAdminDashboardHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    AdminDashboardService adminDashboardService;
    @Mock
    Function<Object, String> defaultResponseWriter;
    @Mock
    AuthenticationStrategy authenticationStrategy;
    @InjectMocks
    GetAdminDashboardHandler getAdminDashboardHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleAuthenticated() throws Exception {
        when(adminDashboardService.getTotalUsersNumber()).thenReturn(null);
        when(adminDashboardService.getActiveUsersNumber()).thenReturn(null);
        when(adminDashboardService.getTotalClientHostsNumber()).thenReturn(null);
        when(adminDashboardService.getActiveClientHostsNumber()).thenReturn(null);
        when(adminDashboardService.getTotalSentEmailsNumber()).thenReturn(null);
        when(adminDashboardService.getTotalActiveChecksNumber()).thenReturn(null);

        getAdminDashboardHandler.handleAuthenticated(null, new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testHandle() throws Exception {
        when(authenticationStrategy.authenticate(any())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));

        getAdminDashboardHandler.handle(null);
    }
}