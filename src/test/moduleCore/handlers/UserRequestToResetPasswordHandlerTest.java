package moduleCore.handlers;

import moduleCore.dto.UserRequestToResetPasswordDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserResetPasswordRequest;
import moduleCore.entities.UserStatus;
import moduleCore.services.MailService;
import moduleCore.services.UserService;
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

public class UserRequestToResetPasswordHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    MailService mailService;
    @Mock
    Function<String, UserRequestToResetPasswordDTO> dtoConverter;
    @InjectMocks
    UserRequestToResetPasswordHandler userRequestToResetPasswordHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
        when(userService.createResetPasswordRequestFor(any())).thenReturn(new UserResetPasswordRequest(null, new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), null, null));

        userRequestToResetPasswordHandler.handle(null);
    }
}
