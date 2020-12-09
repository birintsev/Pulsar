package moduleCore.exceptions;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

public class JsonHttpResponseExceptionTest {
    @Mock
    Map<String, String> details;
    @Mock
    Object backtrace;
    @Mock
    Throwable cause;
    @Mock
    List<Throwable> SUPPRESSED_SENTINEL;
    @Mock
    List<Throwable> suppressedExceptions;
    @InjectMocks
    JsonHttpResponseException jsonHttpResponseException;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
