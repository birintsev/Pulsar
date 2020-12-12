package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.guice.modules.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import javax.validation.Validator;
import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.Scheduler;
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
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessQrtzService;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService;

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
        SessionFactory sessionFactory,
        ClientHostService clientHostService,
        UserService userService
    ) {
        return new ClientHostStatisticServiceImpl(
            sessionFactory,
            clientHostService,
            userService
        );
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
    HttpAccessibilityService accessibilityService(
        SessionFactory sessionFactory,
        Scheduler scheduler,
        @Named("ActiveTrackerJobClass")
            Class<? extends Job> activeTrackerJobClass
    ) {
        return new HttpAccessQrtzService(
            sessionFactory,
            scheduler,
            activeTrackerJobClass
        );
    }

    @Provides
    AdminDashboardService adminDashboardService(SessionFactory sessionFactory) {
        return new AdminDashboardServiceImpl(
            sessionFactory
        );
    }
}
