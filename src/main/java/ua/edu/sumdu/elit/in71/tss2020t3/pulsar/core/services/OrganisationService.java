package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.util.Set;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.busineeslogic.UserStatusException;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.exceptions.busineeslogic.AlreadyExistsException;

public interface OrganisationService {

    /**
     * Creates a new organisation
     * {@link Organisation#getOwner() owned} by passed user
     *
     * @param     organisationName    a name of the orgatisation to be created
     * @param     owner               the organisation owner (creator)
     * @return                        a new organisation
     * @throws    UserStatusException if the user does not have
     *                                {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_PREMIUM_ACCOUNT}
     *                                status
     * */
    Organisation create(String organisationName, User owner)
        throws UserStatusException;

    /**
     * Adds passed user to the list of the
     * {@link Organisation#getMembers() organisation members}.
     * <p>
     * If passed user already is a member of passed organisation,
     * no actions will be performed.
     *
     * @param     user           a user to be added to the organisation members
     * @param     organisationId an organisation to which the user will enter
     * @throws    UserStatusException
     *                           if the {@link User user} does not have
     *                           {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserStatus#USER_STATUS_PREMIUM_ACCOUNT premium account status}
     * @throws    AlreadyExistsException
     *                           if the {@link User user} is a member of the
     *                           {@link Organisation organisation} already
     * @exception java.util.NoSuchElementException
     *                           if the {@link Organisation}
     *                           with such {@link Organisation.ID id}
     *                           does not exist
     * */
    void addToOrganisationMembers(User user, Organisation.ID organisationId)
        throws UserStatusException, AlreadyExistsException;

    /**
     * Finds an {@link Organisation} by identifier
     *
     * @param  organisationId an identifier of the {@link Organisation}
     *                        being searched
     * @return                the {@link Organisation} being searched
     *                        or {@code null}
     *                        if such {@link Organisation} does not exist
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
     * to which the {@link User user} belongs
     *
     * @param   user a user of organisations
     * @return       a set of {@link Organisation} to which the user now belongs
     *               as a {@link Organisation#getMembers member}
     *               or as an {@link Organisation#getOwner owner}
     * */
    Set<Organisation> getByMember(User user);

    /**
     * Returns all the existing {@link Organisation organisations}
     *
     * @return all the existing {@link Organisation organisations}
     * */
    Set<Organisation> getAll();
}
