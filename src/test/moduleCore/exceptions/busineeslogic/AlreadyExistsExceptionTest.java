package moduleCore.exceptions.busineeslogic;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class AlreadyExistsExceptionTest {
    @Mock
    Object backtrace;
    @Mock
    Throwable cause;
    @Mock
    List<Throwable> SUPPRESSED_SENTINEL;
    @Mock
    List<Throwable> suppressedExceptions;
    @InjectMocks
    AlreadyExistsException alreadyExistsException;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
