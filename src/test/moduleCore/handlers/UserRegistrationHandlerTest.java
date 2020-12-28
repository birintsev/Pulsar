package moduleCore.handlers;

import moduleCore.dto.UserRegistrationDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserRegistrationConfirmation;
import moduleCore.entities.UserStatus;
import moduleCore.services.MailService;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class UserRegistrationHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    Validator validator;
    @Mock
    Function<UserRegistrationDTO, User> dtoConverter;
    @Mock
    Function<String, UserRegistrationDTO> deserializer;
    @Mock
    UserService userService;
    @Mock
    MailService mailService;
    @InjectMocks
    UserRegistrationHandler userRegistrationHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.registerUser(any())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
        when(userService.getRegistrationConfirmationFor(any())).thenReturn(new UserRegistrationConfirmation(null, null, null, null));

        userRegistrationHandler.handle(null);
    }
}
