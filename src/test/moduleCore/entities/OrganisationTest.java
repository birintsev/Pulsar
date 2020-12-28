package moduleCore.entities;

import org.junit.Assert;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OrganisationTest {
    @Mock
    User owner;
    @Mock
    Set<User> members;
    @InjectMocks
    Organisation organisation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetId() throws Exception {
        Organisation.ID result = organisation.getId();
        Assert.assertEquals(new Organisation.ID(null), result);
    }

    @Test
    public void testSetId() throws Exception {
        organisation.setId(null);
    }

    @Test
    public void testSetName() throws Exception {
        organisation.setName("name");
    }

    @Test
    public void testSetOwner() throws Exception {
        organisation.setOwner(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
    }

    @Test
    public void testSetMembers() throws Exception {
        organisation.setMembers(new HashSet<User>(Arrays.asList(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))))));
    }

    @Test
    public void testHashCode() throws Exception {
        int result = organisation.hashCode();
        Assert.assertEquals(1153928782, result);
    }

    @Test
    public void testToString() throws Exception {
        String result = organisation.toString();
        Assert.assertEquals("Organisation(id=Organisation.ID(organisationId=null), name=null, owner=owner, members=members)", result);
    }
}
