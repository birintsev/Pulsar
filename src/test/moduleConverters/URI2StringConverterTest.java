package moduleConverters;

import org.junit.Assert;
import org.testng.annotations.Test;

import java.net.URI;

public class URI2StringConverterTest {
    URI2StringConverter uRI2StringConverter = new URI2StringConverter();

    @Test
    public void testApply() throws Exception {
        String result = uRI2StringConverter.apply(new URI("testUri"));
        Assert.assertEquals("testUri", result);
    }
}
