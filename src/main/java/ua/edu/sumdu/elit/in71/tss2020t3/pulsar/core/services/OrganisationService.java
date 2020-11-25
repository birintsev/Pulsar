package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.Set;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;

public interface OrganisationService {

    /**
     * Creates a new organisation
     * {@link Organisation#getOwner() owned} by passed user
     *
     * @param     organisationName  a name of the orgatisation to be created
     * @param     owner             the organisation owner (creator)
     * @return                      a new organisation
     * @exception ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.UserStatusException
     *                              if the user does not have
     *                              {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_PREMIUM_ACCOUNT}
     *                              status
     * */
    Organisation create(String organisationName, User owner);

    /**
     * Adds passed user to the list of the
     * {@link Organisation#getMembers() organisation members}.
     * <p>
     * If passed user already is a member of passed organisation,
     * no actions will be performed.
     *
     * @param user         a user to be added to the organisation members
     * @param organisation an organisation to which the user will enter
     * */
    void addToOrganisationMembers(User user, Organisation organisation);

    /**
     * Finds an {@link Organisation} by identifier
     *
     * @param organisationId an identifier of the {@link Organisation} being searched
     * @return the {@link Organisation} being searched or {@code null} if such {@link Organisation} does not exist
     * */
    Organisation findById(Organisation.ID organisationId);

    /**
     * Provides information whether the {@link User user}
     * is a member of passed {@link Organisation organisation}
     *
     * @param user         checked user
     * @param organisation checked organisation
     * @return             {@code true} if the user is a member
     *                     of the organisation, {@code false} otherwise
     * */
    boolean isMember(User user, Organisation organisation);

    /**
     * Returns all the {@link ClientHost ClientHosts}
     * shared by the {@link Organisation organisation} members
     *
     * @param   organisation an organisation
     * @return               a set of {@link ClientHost ClientHosts}
     *                       owned by the organisation
     *                       {@link Organisation#getMembers() members}
     * */
    Set<ClientHost> getOrganisationClientHosts(Organisation organisation);

    /**
     * Returns all the {@link Organisation organisations}
     * to which the {@link User member} belongs
     *
     * @param member a member of organisations
     * @return a set of {@link Organisation} to which the member now belongs
     * */
    Set<Organisation> getByMember(User member);

    /**
     * Returns all the existing {@link Organisation organisations}
     *
     * @return all the existing {@link Organisation organisations}
     * */
    Set<Organisation> getAll();
}
