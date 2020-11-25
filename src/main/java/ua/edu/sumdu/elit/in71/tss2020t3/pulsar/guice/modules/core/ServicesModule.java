package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javax.validation.Validator;
import org.hibernate.SessionFactory;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.DatabaseUserService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.MailService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.OrganisationServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.SMTPService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserService;

/**
 * A Guice injection configuration module for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services business logic providers}
 * */
public class ServicesModule extends AbstractModule {

    @Provides
    UserService userService(SessionFactory sessionFactory) {
        return new DatabaseUserService(sessionFactory);
    }

    @Provides
    ClientHostService clientHostService(
        UserService userService,
        SessionFactory sessionFactory,
        Validator validator
    ) {
        return new ClientHostServiceImpl(
            userService,
            sessionFactory,
            validator
        );
    }

    @Provides
    ClientHostStatisticService clientHostStatisticService(
        SessionFactory sessionFactory
    ) {
        return new ClientHostStatisticServiceImpl(sessionFactory);
    }

    @Provides
    OrganisationService organisationService(
        SessionFactory sessionFactory,
        UserService userService
    ) {
        return new OrganisationServiceImpl(
            sessionFactory,
            userService
        );
    }

    @Provides
    MailService mailService() {
        return new SMTPService();
    }
}
