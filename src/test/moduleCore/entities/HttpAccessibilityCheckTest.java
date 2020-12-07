package moduleCore.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;

public class HttpAccessibilityCheckTest {
    @Mock
    User user;
    @InjectMocks
    HttpAccessibilityCheck httpAccessibilityCheck;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetRequestId() throws Exception {
        httpAccessibilityCheck.setRequestId(null);
    }

    @Test
    public void testSetTargetUrl() throws Exception {
        httpAccessibilityCheck.setTargetUrl(null);
    }

    @Test
    public void testSetUser() throws Exception {
        httpAccessibilityCheck.setUser(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testSetCheckedWhen() throws Exception {
        httpAccessibilityCheck.setCheckedWhen(null);
    }

    @Test
    public void testSetResponseCode() throws Exception {
        httpAccessibilityCheck.setResponseCode(Integer.valueOf(0));
    }

    @Test
    public void testSetTimeoutChronoUnits() throws Exception {
        httpAccessibilityCheck.setTimeoutChronoUnits("timeoutChronoUnits");
    }

    @Test
    public void testSetResponseTime() throws Exception {
        httpAccessibilityCheck.setResponseTime(Double.valueOf(0));
    }

    @Test
    public void testSetDescription() throws Exception {
        httpAccessibilityCheck.setDescription("description");
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = httpAccessibilityCheck.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = httpAccessibilityCheck.canEqual("other");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = httpAccessibilityCheck.hashCode();
        Assert.assertEquals(-116170898, result);
    }

    @Test
    public void testToString() throws Exception {
        String result = httpAccessibilityCheck.toString();
        Assert.assertEquals("HttpAccessibilityCheck(requestId=null, targetUrl=null, user=user, checkedWhen=null, responseCode=null, timeoutChronoUnits=null, responseTime=null, description=null)", result);
    }
}
