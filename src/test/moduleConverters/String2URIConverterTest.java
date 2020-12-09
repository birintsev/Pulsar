package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class String2URIConverterTest {
    String2URIConverter string2URIConverter = new String2URIConverter();

    @Test
    public void testApply() throws Exception {
        URI result = string2URIConverter.apply("testURI");
        Assert.assertEquals(new URI("testURI"), result);
    }
}
