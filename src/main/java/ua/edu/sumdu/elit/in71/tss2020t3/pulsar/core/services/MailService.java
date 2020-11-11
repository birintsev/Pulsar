package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest;

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

    /**
     * Sends an email with a link to reset password page to the
     * {@link UserResetPasswordRequest#getUser() user's} mailbox
     *
     * @param     userResetPasswordRequest a JPA POJO with a key
     *                                     for resetting user's password
     * */
    void sendResetPasswordEmail(
        UserResetPasswordRequest userResetPasswordRequest
    );
}
