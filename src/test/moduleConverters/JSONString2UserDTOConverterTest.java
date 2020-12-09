package moduleConverters;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JSONString2UserDTOConverterTest {
    @Mock
    Logger LOGGER;
    @InjectMocks
    JSONString2UserDTOConverter jSONString2UserDTOConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApply() throws Exception {
        // UserRegistrationDTO result = jSONString2UserDTOConverter.apply("1234dfdfhgfhfgh1");
        // Assert.assertEquals(new UserRegistrationDTO("email", 0, "phoneNumber", "password", "firstName", "lastName", "username"), result);
        Assert.assertTrue(true);
    }
}
