package moduleCore.entities.client;

import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.mockito.Mockito.*;

public class DiskInfoTest {
    @Mock
    ClientHostStatistic clientHostStatistic;
    @Mock
    BigInteger free;
    @Mock
    BigInteger total;
    @InjectMocks
    DiskInfo diskInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        when(clientHostStatistic.getId()).thenReturn(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(), null));

        String result = diskInfo.toString();
        Assert.assertEquals("DiskInfo{clientHostStatistic=ClientHostStatisticID{clientHost=null, clientLocalTime=null}, origin='null', free=free, total=total}", result);
    }


    @Test
    public void testSetOrigin() throws Exception {
        diskInfo.setOrigin("origin");
    }

    @Test
    public void testSetFree() throws Exception {
        diskInfo.setFree(null);
    }

    @Test
    public void testSetTotal() throws Exception {
        diskInfo.setTotal(null);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = diskInfo.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = diskInfo.canEqual("other");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = diskInfo.hashCode();
        Assert.assertEquals(960589741, result);
    }
}
