package moduleConverters;

import org.junit.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;

public class BigInteger2StringConverterTest {
    BigInteger2StringConverter bigInteger2StringConverter = new BigInteger2StringConverter();

    @Test
    public void testApply() throws Exception {
        String result = bigInteger2StringConverter.apply(BigInteger.valueOf(123456));
        Assert.assertEquals("123456", result);
    }

    @Test
    public void testConvert() throws Exception {
        String result = bigInteger2StringConverter.convert(BigInteger.valueOf(123456));
        Assert.assertEquals("123456", result);
    }
}
