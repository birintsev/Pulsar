package moduleCore.handlers;

import moduleCore.entities.User;
import moduleCore.entities.UserRegistrationConfirmation;
import moduleCore.entities.UserStatus;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RegistrationConfirmationHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @InjectMocks
    RegistrationConfirmationHandler registrationConfirmationHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.getRegistrationConfirmationBy((UUID) any())).thenReturn(new UserRegistrationConfirmation(new UserRegistrationConfirmation.ID(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status"))))), null, null, null));

        registrationConfirmationHandler.handle(null);
    }
}
