package moduleCore.handlers;

import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.entities.client.*;
import moduleCore.services.ClientHostService;
import moduleCore.services.ClientHostStatisticService;
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

public class GetClientHostStatisticHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    ClientHostService clientHostService;
    @Mock
    ClientHostStatisticService clientHostStatisticService;
    @Mock
    Validator validator;
    @Mock
    Function<ClientHostStatistic, ClientHostStatisticDTO> responseConverter;
    @InjectMocks
    GetClientHostStatisticHandler getClientHostStatisticHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
        when(userService.isUserPremiumAccount(any())).thenReturn(true);
        when(clientHostService.subscriberOrOwner(any(), anyString())).thenReturn(true);
        when(clientHostStatisticService.getByPublicKey(anyString())).thenReturn(Arrays.<ClientHostStatistic>asList(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, "agentVersion", new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), new MemoryInfo(null, null, null, null, null, null), new HashSet<NetworkInfo>(Arrays.asList(new NetworkInfo(null, "name", null, null))))));
        when(clientHostStatisticService.getByPublicKey(anyString(), any(), any())).thenReturn(Arrays.<ClientHostStatistic>asList(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, "agentVersion", new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), new MemoryInfo(null, null, null, null, null, null), new HashSet<NetworkInfo>(Arrays.asList(new NetworkInfo(null, "name", null, null))))));

        getClientHostStatisticHandler.handle(null);
    }
}
