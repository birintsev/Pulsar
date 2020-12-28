package moduleCore.handlers;

import moduleCore.dto.JoinOrganisationRequest;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.handlers.templates.HandlerAuthenticator;
import moduleCore.handlers.templates.HandlerValidator;
import moduleCore.services.OrganisationService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class JoinOrganisationHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    OrganisationService organisationService;
    @Mock
    HandlerAuthenticator authenticator;
    @InjectMocks
    JoinOrganisationHandler joinOrganisationHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleUserRequest() throws Exception {
        joinOrganisationHandler.handleUserRequest(new JoinOrganisationRequest(null), new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), null);
    }

    @Test
    public void testHandle() throws Exception {
        joinOrganisationHandler.handle(null);
    }
}
