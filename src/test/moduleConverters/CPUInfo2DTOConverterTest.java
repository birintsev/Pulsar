package moduleConverters;

import org.junit.Assert;
import org.junit.Test;
import moduleCore.dto.ClientHostStatisticDTO;
import moduleCore.entities.client.CPUInfo;

public class CPUInfo2DTOConverterTest {
    CPUInfo2DTOConverter cPUInfo2DTOConverter = new CPUInfo2DTOConverter();

    @Test
    public void testApply() throws Exception {
        ClientHostStatisticDTO.CPUInfoDTO result = cPUInfo2DTOConverter.apply(new CPUInfo());
        Assert.assertEquals(new ClientHostStatisticDTO.CPUInfoDTO(), result);
    }
}
