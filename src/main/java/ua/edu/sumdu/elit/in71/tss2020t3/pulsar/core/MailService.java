package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;

/**
 * A class providing methods for mailing
 * */
public interface MailService {
    /**
     * Sends an email to user with registration confirmation URL
     *
     * @param     confirmation          a POJO-container for the user and the
     *                                  confirmation key for him/her
     * @exception IllegalStateException if the {@code confirmation} is not valid
     *                                  (e.g. its user is not set or is invalid)
     * */
    void sendRegistrationConfirmationEmail(
        UserRegistrationConfirmation confirmation
    );
}
