package moduleCore.handlers;

import moduleCore.dto.CreateOrganisationRequest;
import moduleCore.entities.Organisation;
import moduleCore.entities.User;
import moduleCore.entities.UserStatus;
import moduleCore.services.OrganisationService;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class CreateOrganisationHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    OrganisationService organisationService;
    @Mock
    Function<String, CreateOrganisationRequest> bodyConverter;
    @Mock
    Validator validator;
    @InjectMocks
    CreateOrganisationHandler createOrganisationHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))));
        when(userService.isUserPremiumAccount(any())).thenReturn(true);
        when(organisationService.create(anyString(), any())).thenReturn(new Organisation(null, "name", new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status")))), new HashSet<User>(Arrays.asList(new User(new User.UserID("email"), "username", "firstName", "lastName", 0, "phoneNumber", "password", new HashSet<UserStatus>(Arrays.asList(new UserStatus("status"))))))));

        createOrganisationHandler.handle(null);
    }
}
