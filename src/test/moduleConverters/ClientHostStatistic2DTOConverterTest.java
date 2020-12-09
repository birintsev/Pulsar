package moduleConverters;

import moduleCore.entities.client.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

public class ClientHostStatistic2DTOConverterTest {
    @Mock
    Function<CPUInfo, ClientHostStatisticDTO.CPUInfoDTO> cpuInfoConverter;
    @Mock
    Function<DiskInfo, ClientHostStatisticDTO.DiskInfoDTO> diskInfoConverter;
    @Mock
    Function<NetworkInfo, ClientHostStatisticDTO.NetworkInfoDTO> networkInfoConverter;
    @Mock
    Function<MemoryInfo, ClientHostStatisticDTO.MemoryInfoDTO> memoryInfoConverter;
    @InjectMocks
    ClientHostStatistic2DTOConverter clientHostStatistic2DTOConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO result = clientHostStatistic2DTOConverter.apply(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, null, new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), new MemoryInfo(null, null, null, null, null, null), new HashSet<NetworkInfo>(Arrays.asList(new NetworkInfo(null, "name", null, null)))));
        Assert.assertEquals("ClientHostStatisticDTO(host=host, clientLocalTime=null, bootTime=null, publicKey=null, agentVersion=null, cpuInfoList=[null], disksInfo=[null], loadAverage=[0.0], memoryInfo=null, networksInfo=[null])", result.toString());
    }
}
