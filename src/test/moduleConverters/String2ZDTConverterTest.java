package moduleConverters;

import org.junit.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.time.ZonedDateTime;

public class String2ZDTConverterTest {
    String2ZDTConverter string2ZDTConverter = new String2ZDTConverter();

    @Test
    public void testApply() throws Exception {
        ZonedDateTime result = string2ZDTConverter.apply("testURI");
        Assert.assertEquals(new URI("testURI"), result);
    }

    @Test
    public void testConvert() throws Exception {
        ZonedDateTime result = string2ZDTConverter.convert("testURI");
        Assert.assertEquals(new URI("testURI"), result);
    }
}
