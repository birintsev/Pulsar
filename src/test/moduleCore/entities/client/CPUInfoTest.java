package moduleCore.entities.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CPUInfoTest {
    @Mock
    ClientHostStatistic clientHostStatistic;
    @InjectMocks
    CPUInfo cPUInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        when(clientHostStatistic.getId()).thenReturn(new ClientHostStatistic.ClientHostStatisticID(new ClientHost(), null));

        String result = cPUInfo.toString();
        Assert.assertEquals("CPUInfo{clientHostStatistic=ClientHostStatisticID{clientHost=null, clientLocalTime=null}, num=0.0, user=0.0, system=0.0, idle=0.0}", result);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = cPUInfo.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testSetNum() throws Exception {
        cPUInfo.setNum(0d);
    }

    @Test
    public void testSetUser() throws Exception {
        cPUInfo.setUser(0d);
    }

    @Test
    public void testSetSystem() throws Exception {
        cPUInfo.setSystem(0d);
    }

    @Test
    public void testSetIdle() throws Exception {
        cPUInfo.setIdle(0d);
    }
}