package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.HashSet;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException;

/**
 * This is a default implementation of {@link OrganisationService}
 * */
@AllArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {

    private static final Logger LOGGER = Logger.getLogger(
        OrganisationServiceImpl.class
    );

    private final SessionFactory sessionFactory;

    private final UserService userService;

    @Override
    public void addToOrganisationMembers(User user, Organisation organisation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            organisation.getMembers().add(user);
            session.update(organisation);
            session.flush();
            t.commit();
        }
    }

    @Override
    public Organisation create(String organisationName, User owner) {
        Organisation organisation;
        if (!userService.isUserPremiumAccount(owner)) {
            throw new UserStatusException(
                "The user does not have premium account status"
            );
        }
        try (Session session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();
            organisation = new Organisation(
                null,
                organisationName,
                owner,
                new HashSet<>()
            );
            session.save(organisation);
            session.flush();
            t.commit();
        }
        return organisation;
    }
}
