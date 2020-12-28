package moduleCore.handlers;

import moduleCore.entities.Organisation;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.entities.client.ClientHost;
import moduleCore.services.ClientHostService;
import moduleCore.services.OrganisationService;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class GetUserClientHostsHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    ClientHostService clientHostService;
    @Mock
    OrganisationService organisationService;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    GetUserClientHostsHandler getUserClientHostsHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
        when(clientHostService.getByOwner(any())).thenReturn(new HashSet<ClientHost>(Arrays.asList(new ClientHost(null, "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"))));
        when(clientHostService.getBySubscriber(any())).thenReturn(new HashSet<ClientHost>(Arrays.asList(new ClientHost(null, "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"))));
        when(organisationService.getOrganisationClientHosts(any())).thenReturn(new HashSet<ClientHost>(Arrays.asList(new ClientHost(null, "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"))));
        when(organisationService.getByMember(any())).thenReturn(new HashSet<Organisation>(Arrays.asList(new Organisation(null, "name", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), new HashSet<User>(Arrays.asList(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status"))))))))));

        getUserClientHostsHandler.handle(null);
    }
}
