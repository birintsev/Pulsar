package moduleCore.handlers;

import moduleCore.dto.UserResetPasswordDTO;
import moduleCore.services.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validator;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class UserResetPasswordHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    UserService userService;
    @Mock
    Function<String, UserResetPasswordDTO> dtoConverter;
    @Mock
    Validator validator;
    @InjectMocks
    UserResetPasswordHandler userResetPasswordHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        userResetPasswordHandler.handle(null);
    }
}
