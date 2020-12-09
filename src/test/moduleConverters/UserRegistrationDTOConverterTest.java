package moduleConverters;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import moduleCore.dto.UserRegistrationDTO;
import moduleCore.entities.User;

import javax.validation.Validator;

public class UserRegistrationDTOConverterTest {
    @Mock
    Logger LOGGER;
    @Mock
    Validator validator;
    @InjectMocks
    UserRegistrationDTOConverter userRegistrationDTOConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApply() throws Exception {
        User result = userRegistrationDTOConverter.apply(new UserRegistrationDTO("email", 0, "phoneNumber", "password", "firstName", "lastName", "username"));
        Assert.assertEquals("User(id=UserID{email='email'}, username=username, firstName=firstName, lastName=lastName, age=0, phoneNumber=phoneNumber, password=password, userStatuses=[])", result.toString());
    }
}
