package moduleCore.handlers;

import moduleCore.entities.Organisation;
import moduleCore.entities.User;
import moduleCore.services.OrganisationService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Mockito.*;

public class GetOrganisationsHandlerTest {
    @Mock
    Logger LOGGER;
    @Mock
    OrganisationService organisationService;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    GetOrganisationsHandler getOrganisationsHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandle() throws Exception {
        when(organisationService.getAll()).thenReturn(new HashSet<Organisation>(Arrays.asList(new Organisation(null, "name", null, new HashSet<User>(Arrays.asList(null))))));

        getOrganisationsHandler.handle(null);
    }
}
