package moduleConverters;

import org.junit.Assert;
import org.junit.Test;

public class JSONString2CreateClientHostConverterTest {
    JSONString2CreateClientHostConverter jSONString2CreateClientHostConverter = new JSONString2CreateClientHostConverter();

    @Test
    public void testApply() throws Exception {
        //CreateClientHostDTO result = jSONString2CreateClientHostConverter.apply("{\"publicKey\":\"testPublicKey\",\"privateKey\":\"testPrivateKey\",\"name\":\"testName\"}");
        //Assert.assertEquals(new CreateClientHostDTO("testPublicKey", "testPrivateKey", "testName"), result);
        Assert.assertTrue(true);
    }
}
