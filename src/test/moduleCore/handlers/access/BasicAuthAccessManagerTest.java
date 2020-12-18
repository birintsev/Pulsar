package moduleCore.handlers.access;

import io.javalin.core.security.Role;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class BasicAuthAccessManagerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @InjectMocks
    BasicAuthAccessManager basicAuthAccessManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testManage() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));

        basicAuthAccessManager.manage(null, null, new HashSet<Role>(Arrays.asList(new UserStatus("status"))));
    }
}
