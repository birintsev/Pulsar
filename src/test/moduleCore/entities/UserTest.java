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
import java.util.Set;

public class UserTest {
    @Mock
    User.UserID id;
    @Mock
    Set<UserStatus> userStatuses;
    @InjectMocks
    User user = new UserObject().getUserObject();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetId() throws Exception {
        user.setId(new User.UserID("email"));
    }

    @Test
    public void testSetUsername() throws Exception {
        user.setUsername("username");
    }

    @Test
    public void testSetFirstName() throws Exception {
        user.setFirstName("firstName");
    }

    @Test
    public void testSetLastName() throws Exception {
        user.setLastName("lastName");
    }

    @Test
    public void testSetAge() throws Exception {
        user.setAge(0);
    }

    @Test
    public void testSetPhoneNumber() throws Exception {
        user.setPhoneNumber("phoneNumber");
    }

    @Test
    public void testSetPassword() throws Exception {
        user.setPassword("password");
    }

    @Test
    public void testSetUserStatuses() throws Exception {
        user.setUserStatuses(new HashSet<UserStatus>(Arrays.asList(new UserStatus("status"))));
    }

    @Test
    public void testToString() throws Exception {
        String result = user.toString();
        Assert.assertEquals("User(id=id, username=Username, firstName=Test, lastName=Test, age=18, phoneNumber=+389, password=pass, userStatuses=userStatuses)", result);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = user.equals(new UserObject().getUserObject());
        Assert.assertEquals(false, result);
    }

    @Test
    public void testCanEqual() throws Exception {
        boolean result = user.canEqual("User(id=id, username=Username, firstName=Test, lastName=Test, age=18, phoneNumber=+389, password=pass, userStatuses=userStatuses)");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = user.hashCode();
        Assert.assertEquals(-1552896950, result);
    }
}
