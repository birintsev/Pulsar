package moduleConverters;

import moduleCore.entities.client.*;
import org.junit.Assert;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;

import java.util.Arrays;
import java.util.HashSet;

public class NetworkInfo2DTOConverterTest {
    NetworkInfo2DTOConverter networkInfo2DTOConverter = new NetworkInfo2DTOConverter();

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO.NetworkInfoDTO result = networkInfo2DTOConverter.apply(new NetworkInfo(new ClientHostStatistic(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(new ClientHost.ID("privateKey"), "publicKey", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), "name"), null), "host", null, "agentVersion", new HashSet<LoadAverage>(Arrays.asList(new LoadAverage(null, Integer.valueOf(0), Double.valueOf(0)))), new HashSet<CPUInfo>(Arrays.asList(new CPUInfo())), new HashSet<DiskInfo>(Arrays.asList(new DiskInfo())), new MemoryInfo(null, null, null, null, null, null), new HashSet<NetworkInfo>(Arrays.asList(null))), "name", null, null));
        Assert.assertEquals(new ClientHostStatisticDTO.NetworkInfoDTO("name", null, null), result);
    }
}
