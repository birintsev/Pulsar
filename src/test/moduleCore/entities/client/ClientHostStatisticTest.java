package moduleCore.entities.client;

import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClientHostStatisticTest {
    @Mock
    Logger LOGGER;
    @Mock
    ClientHostStatistic.ClientHostStatisticID id;
    @Mock
    Set<LoadAverage> loadAverage;
    @Mock
    Set<CPUInfo> cpuInfoList;
    @Mock
    Set<DiskInfo> disksInfo;
    @Mock
    MemoryInfo memoryInfo;
    @Mock
    Set<NetworkInfo> networksInfo;
    @InjectMocks
    ClientHostStatistic clientHostStatistic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = clientHostStatistic.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testSetId() throws Exception {
        clientHostStatistic.setId(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null));
    }

    @Test
    public void testSetHost() throws Exception {
        clientHostStatistic.setHost("host");
    }

    @Test
    public void testSetBootTime() throws Exception {
        clientHostStatistic.setBootTime(null);
    }

    @Test
    public void testSetAgentVersion() throws Exception {
        clientHostStatistic.setAgentVersion("agentVersion");
    }


    @Test
    public void testSetCpuInfoList() throws Exception {
        clientHostStatistic.setCpuInfoList(new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())));
    }

    @Test
    public void testSetDisksInfo() throws Exception {
        clientHostStatistic.setDisksInfo(new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())));
    }

    @Test
    public void testSetMemoryInfo() throws Exception {
        clientHostStatistic.setMemoryInfo(new MemoryInfo(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, "agentVersion", new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), null, new HashSet<NetworkInfo>(Arrays.asList(new NetworkInfo(null, "name", null, null)))), null, null, null, null, null));
    }

    @Test
    public void testToString() throws Exception {
        String result = clientHostStatistic.toString();
        Assert.assertEquals("ClientHostStatistic(id=id, host=null, bootTime=null, agentVersion=null, loadAverage=loadAverage, cpuInfoList=loadAverage, disksInfo=loadAverage, memoryInfo=memoryInfo, networksInfo=loadAverage)", result);
    }
}
