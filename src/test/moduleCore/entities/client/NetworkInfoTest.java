package moduleCore.entities.client;

import moduleCore.entities.client.objects.ClientHostObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class NetworkInfoTest {
    @Mock
    ClientHostStatistic clientHostStatistic;
    @Mock
    BigInteger in;
    @Mock
    BigInteger out;
    @InjectMocks
    NetworkInfo networkInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        when(clientHostStatistic.getId()).thenReturn(new ClientHostStatistic.ClientHostStatisticID(new ClientHostObject().getClientHost(), null));

        String result = networkInfo.toString();
        Assert.assertEquals("NetworkInfo{clientHostStatistic=ClientHostStatisticID{clientHost=publickey, clientLocalTime=null}, name='null', in=in, out=in}", result);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = networkInfo.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testSetName() throws Exception {
        networkInfo.setName("name");
    }

    @Test
    public void testSetIn() throws Exception {
        networkInfo.setIn(null);
    }

    @Test
    public void testSetOut() throws Exception {
        networkInfo.setOut(null);
    }
}
