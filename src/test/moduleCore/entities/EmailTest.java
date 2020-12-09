package moduleCore.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import moduleCore.entities.client.objects.UserObject;

import java.util.Arrays;
import java.util.HashSet;

public class EmailTest {
    @Mock
    User user = new UserObject().getUserObject();
    @InjectMocks
    Email email;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetEmailId() throws Exception {
        email.setEmailId(null);
    }

    @Test
    public void testSetUser() throws Exception {
        email.setUser(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testSetSubject() throws Exception {
        email.setSubject("subject");
    }

    @Test
    public void testSetBody() throws Exception {
        email.setBody("body");
    }

    @Test
    public void testSetSentWhen() throws Exception {
        email.setSentWhen(null);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = email.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = email.canEqual("other");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = email.hashCode();
        Assert.assertEquals(-1182094342, result);
    }

    @Test
    public void testToString() throws Exception {
        String result = email.toString();
        Assert.assertEquals("Email(emailId=null, user=user, subject=null, body=null, sentWhen=null)", result);
    }
}
