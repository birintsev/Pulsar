package moduleConverters;

import org.junit.Assert;
import org.junit.Test;
import moduleCore.dto.ClientHostStatisticDTO;

public class ServerStatisticDTO2StringConverterTest {
    ServerStatisticDTO2StringConverter serverStatisticDTO2StringConverter = new ServerStatisticDTO2StringConverter();

    @Test
    public void testApply() throws Exception {
        String result = serverStatisticDTO2StringConverter.apply(new ClientHostStatisticDTO());
        Assert.assertEquals("{\"host\":null,\"at\":null,\"boot_time\":null,\"public_key\":null,\"agent_version\":null,\"cpu\":null,\"disks\":null,\"load\":null,\"memory\":null,\"network\":null}", result);
    }
}