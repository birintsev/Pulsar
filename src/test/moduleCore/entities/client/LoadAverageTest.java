package moduleCore.entities.client;

import moduleCore.entities.client.objects.ClientHostObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoadAverageTest {
    @Mock
    ClientHostStatistic clientHostStatistic;
    @InjectMocks
    LoadAverage loadAverage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        when(clientHostStatistic.getId()).thenReturn(new ClientHostStatistic.ClientHostStatisticID(new ClientHostObject().getClientHost(), null));

        String result = loadAverage.toString();
        Assert.assertEquals("LoadAverage{clientHostStatistic=ClientHostStatisticID{clientHost=publickey, clientLocalTime=null}, order=null, loadAverage=null}", result);
    }

    @Test
    public void testSetOrder() throws Exception {
        loadAverage.setOrder(Integer.valueOf(0));
    }

    @Test
    public void testSetLoadAverage() throws Exception {
        loadAverage.setLoadAverage(Double.valueOf(0));
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = loadAverage.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = loadAverage.canEqual("other");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = loadAverage.hashCode();
        Assert.assertEquals(-1895497068, result);
    }
}
