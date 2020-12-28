package moduleConverters;

import org.junit.Assert;
import org.testng.annotations.Test;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.client.DiskInfo;

public class DiskInfo2DTOConverterTest {
    DiskInfo2DTOConverter diskInfo2DTOConverter = new DiskInfo2DTOConverter();

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO.DiskInfoDTO result = diskInfo2DTOConverter.apply(new DiskInfo());
        Assert.assertEquals(new ClientHostStatisticDTO.DiskInfoDTO(), result);
    }
}
