package moduleCore.entities.client;

import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;

public class ClientHostTest {
    @Mock
    ClientHost.ID id;
    @Mock
    User owner;
    @InjectMocks
    ClientHost clientHost;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = clientHost.equals("o");
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = clientHost.hashCode();
        Assert.assertEquals(0, result);
    }

    @Test
    public void testSetId() throws Exception {
        clientHost.setId(new ClientHost.ID("privateKey"));
    }

    @Test
    public void testSetPublicKey() throws Exception {
        clientHost.setPublicKey("publicKey");
    }

    @Test
    public void testSetOwner() throws Exception {
        clientHost.setOwner(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testSetName() throws Exception {
        clientHost.setName("name");
    }

    @Test
    public void testToString() throws Exception {
        String result = clientHost.toString();
        Assert.assertEquals("ClientHost(id=id, publicKey=null, owner=owner, name=null)", result);
    }
}
