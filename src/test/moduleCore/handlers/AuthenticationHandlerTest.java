package moduleCore.handlers;

import moduleCore.dto.responses.UserDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.services.security.AuthenticationStrategy;
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

public class AuthenticationHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    ModelMapper modelMapper;
    @Mock
    Function<UserDTO, String> responseConverter;
    @Mock
    AuthenticationStrategy authenticationStrategy;
    @InjectMocks
    AuthenticationHandler authenticationHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(authenticationStrategy.authenticate(any())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));

        authenticationHandler.handle(null);
    }
}

