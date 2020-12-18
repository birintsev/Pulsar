package moduleCore.handlers;

import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.entities.client.ClientHost;
import moduleCore.services.ClientHostService;
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

public class GetAllClientHostsHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    ClientHostService clientHostService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    Function<Object, String> responseConversionStrategy;
    @Mock
    AuthenticationStrategy authenticationStrategy;
    @InjectMocks
    GetAllClientHostsHandler getAllClientHostsHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleAuthenticated() throws Exception {
        when(clientHostService.getAll()).thenReturn(new HashSet<ClientHost>(Arrays.asList(new ClientHost(null, "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"))));

        getAllClientHostsHandler.handleAuthenticated(null, new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testHandle() throws Exception {
        when(authenticationStrategy.authenticate(any())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));

        getAllClientHostsHandler.handle(null);
    }
}
