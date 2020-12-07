package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

public class ZDT2StringConverterTest {
    ZDT2StringConverter zDT2StringConverter = new ZDT2StringConverter();

    @Test
    public void testApply() throws Exception {
        String result = zDT2StringConverter.apply(null);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testConvert() throws Exception {
        String result = zDT2StringConverter.convert(null);
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}