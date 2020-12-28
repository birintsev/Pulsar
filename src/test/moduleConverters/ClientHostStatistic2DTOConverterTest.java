package moduleConverters;

import moduleCore.entities.client.*;
import org.hibernate.validator.constraints.ModCheck;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;

import java.math.BigInteger;
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
    @Mock
    ClientHostStatistic clientHostStatistic = null;
    @Mock
    MemoryInfo memoryInfo = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        clientHostStatistic = new ClientHostStatistic();
        clientHostStatistic.setAgentVersion("1");
        clientHostStatistic.setHost("testhost");
        memoryInfo = new MemoryInfo();
        memoryInfo.setActive(BigInteger.TEN);
        clientHostStatistic.setMemoryInfo(memoryInfo);
    }

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO result = clientHostStatistic2DTOConverter.apply(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(
                new User.UserID("email"), "username", "firstName", "lastName", 17, "phoneNumber", "password",
                new HashSet<>(Arrays.asList(new UserStatus("status")))), "name"), null), "testhost", null, null,
                new HashSet<>(Arrays.asList(new LoadAverage(clientHostStatistic, 15, 15d))),
                new HashSet<>(Arrays.asList(new CPUInfo())), new HashSet<>(Arrays.asList(new DiskInfo())),
                new MemoryInfo(clientHostStatistic, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE),
                new HashSet<>(Arrays.asList(new NetworkInfo(null, "name", BigInteger.ONE, BigInteger.ONE)))));
        Assert.assertEquals("ClientHostStatisticDTO(host=testhost, clientLocalTime=null, bootTime=null, publicKey=null, agentVersion=null, cpuInfoList=[null], disksInfo=[null], loadAverage=[15.0], memoryInfo=null, networksInfo=[null])", result.toString());
    }
}
