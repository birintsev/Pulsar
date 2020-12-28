package moduleCore.entities.client;

import objects.ClientHostObject;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class MemoryInfoTest {
    @Mock
    ClientHostStatistic clientHostStatistic;
    @Mock
    BigInteger wired;
    @Mock
    BigInteger free;
    @Mock
    BigInteger active;
    @Mock
    BigInteger inactive;
    @Mock
    BigInteger total;
    @InjectMocks
    MemoryInfo memoryInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        when(clientHostStatistic.getId()).thenReturn(new ClientHostStatistic.ClientHostStatisticID(new ClientHostObject().getClientHost(), null));

        String result = memoryInfo.toString();
        Assert.assertEquals("MemoryInfo{clientHostStatistic=ClientHostStatisticID{clientHost=publickey, clientLocalTime=null}, wired=active, free=active, active=active, inactive=active, total=active}", result);
    }

    @Test
    public void testSetWired() throws Exception {
        memoryInfo.setWired(null);
    }

    @Test
    public void testSetFree() throws Exception {
        memoryInfo.setFree(null);
    }

    @Test
    public void testSetActive() throws Exception {
        memoryInfo.setActive(null);
    }

    @Test
    public void testSetInactive() throws Exception {
        memoryInfo.setInactive(null);
    }

    @Test
    public void testSetTotal() throws Exception {
        memoryInfo.setTotal(null);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = memoryInfo.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = memoryInfo.canEqual("other");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = memoryInfo.hashCode();
        Assert.assertEquals(663834039, result);
    }
}
