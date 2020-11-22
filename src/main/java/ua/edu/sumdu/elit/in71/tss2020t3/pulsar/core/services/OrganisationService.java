package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.Organisation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

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
     * @param user a user to be added to the organisation members
     * @param organisation an organisation to which the user will enter
     * */
    void addToOrganisationMembers(User user, Organisation organisation);
}
