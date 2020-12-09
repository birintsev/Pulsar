package moduleConverters;

import moduleCore.entities.client.*;
import org.junit.Assert;
import org.junit.Test;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;

import java.util.Arrays;
import java.util.HashSet;

public class MemoryInfo2DTOConverterTest {
    MemoryInfo2DTOConverter memoryInfo2DTOConverter = new MemoryInfo2DTOConverter();

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO.MemoryInfoDTO result = memoryInfo2DTOConverter.apply(new MemoryInfo(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, "agentVersion", new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), null, new HashSet<NetworkInfo>(Arrays.asList(new NetworkInfo(null, "name", null, null)))), null, null, null, null, null));
        Assert.assertEquals(new ClientHostStatisticDTO.MemoryInfoDTO(), result);
    }
}
