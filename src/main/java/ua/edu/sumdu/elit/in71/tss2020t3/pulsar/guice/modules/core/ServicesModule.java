package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import javax.validation.Validator;
import org.hibernate.SessionFactory;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AccessibilityService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AccessibilityServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AdminDashboardService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AdminDashboardServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.ClientHostStatisticServiceImpl;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.UserServiceImpl;
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
        return new UserServiceImpl(sessionFactory);
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
    MailService mailService(SessionFactory sessionFactory) {
        return new SMTPService(
            sessionFactory
        );
    }

    @Provides
    AccessibilityService accessibilityService(SessionFactory sessionFactory) {
        return new AccessibilityServiceImpl(sessionFactory);
    }

    @Provides
    AdminDashboardService adminDashboardService(SessionFactory sessionFactory) {
        return new AdminDashboardServiceImpl(
            sessionFactory
        );
    }
}
