package moduleConverters;

import org.junit.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;

public class String2BigIntegerConverterTest {
    String2BigIntegerConverter string2BigIntegerConverter = new String2BigIntegerConverter();

    @Test
    public void testApply() throws Exception {
        BigInteger result = string2BigIntegerConverter.apply("1234");
        Assert.assertEquals(BigInteger.valueOf(1234), result);
    }

    @Test
    public void testConvert() throws Exception {
        BigInteger result = string2BigIntegerConverter.convert("1234");
        Assert.assertEquals(BigInteger.valueOf(1234), result);
    }
}
