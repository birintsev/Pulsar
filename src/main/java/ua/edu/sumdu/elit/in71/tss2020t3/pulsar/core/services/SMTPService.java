package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.CHARSET_NAME;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.FRONTEND_URL;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.MAIL_USER;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.REGISTRATION_CONFIRMATION_MAIL_BODY_LOCATION;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.REGISTRATION_CONFIRMATION_MAIL_SUBJECT;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.RESET_PASSWORD_MAIL_BODY_LOCATION;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.RESET_PASSWORD_MAIL_SUBJECT;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.SMTP_AUTHENTICATION_ENABLED;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.SMTP_HOST;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.SMTP_MAIL_PASSWORD;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.SMTP_PORT;
import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.ApplicationPropertiesNames.SMTP_STARTTLS_ENABLED;


import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.log4j.Logger;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserRegistrationConfirmation;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.UserResetPasswordRequest;

/**
 * A default implementation of {@link MailService} that uses an SMTP serer
 * to send messages
 * */
public class SMTPService implements MailService {

    private static final Logger LOGGER = Logger.getLogger(SMTPService.class);

    private final Properties smtpProperties;

    /**
     * A default constructor
     * */
    public SMTPService() {
        smtpProperties = new Properties();
        smtpProperties.put(
            "mail.smtp.auth",
            System.getProperty(SMTP_AUTHENTICATION_ENABLED)
        );
        smtpProperties.put(
            "mail.smtp.starttls.enable",
            System.getProperty(SMTP_STARTTLS_ENABLED)
        );
        smtpProperties.put(
            "mail.smtp.host",
            System.getProperty(SMTP_HOST)
        );
        smtpProperties.put(
            "mail.smtp.port",
            System.getProperty(SMTP_PORT)
        );
    }

    @Override
    public void sendRegistrationConfirmationEmail(
        UserRegistrationConfirmation confirmation
    ) {
        Message email = null;
        try {
            email = prepareMessageFor(confirmation);
            LOGGER.trace("Sending the email: " + email);
            Transport.send(email);
            LOGGER.trace("The email " + email + " has been sent");
        } catch (MessagingException e) {
            LOGGER.error(
                "Error during sending an email to the user"
                    + " (registration confirmation). The email is: " + email
            );
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendResetPasswordEmail(
        UserResetPasswordRequest userResetPasswordRequest
    ) {
        try {
            Message email = prepareMessageFor(userResetPasswordRequest);
            LOGGER.trace("Sending the email: " + email);
            Transport.send(email);
            LOGGER.trace("The email " + email + " has been sent");
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    private Message prepareMessageFor(
        UserRegistrationConfirmation confirmation
    ) throws MessagingException {
        return getSimpleMessage(
            confirmation.getId().getUser().getId().getEmail(),
            System.getProperty(
                REGISTRATION_CONFIRMATION_MAIL_SUBJECT
            ),
            fetchEmailBodyFor(confirmation)
        );
    }

    private Message getSimpleMessage(
        String to, String subject, String body
    ) throws MessagingException {
        Message message = new MimeMessage(
            Session.getDefaultInstance(
                smtpProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(
                            System.getProperty(MAIL_USER),
                            System.getProperty(SMTP_MAIL_PASSWORD)
                        );
                    }
                }
            )
        );
        message.setFrom(
            new InternetAddress(
                System.getProperty(MAIL_USER)
            )
        );
        message.setRecipient(
            Message.RecipientType.TO,
            new InternetAddress(to)
        );
        message.setSubject(subject);
        MimeBodyPart bodyContentPart = new MimeBodyPart();
        bodyContentPart.setContent(
            body,
            "text/html; charset=" + System.getProperty(CHARSET_NAME)
        );
        message.setContent(new MimeMultipart(bodyContentPart));
        return message;
    }

    private Message prepareMessageFor(
        UserResetPasswordRequest userResetPasswordRequest
    ) throws MessagingException {
        return getSimpleMessage(
            userResetPasswordRequest.getUser().getId().getEmail(),
            System.getProperty(RESET_PASSWORD_MAIL_SUBJECT),
            fetchEmailBodyFor(userResetPasswordRequest)
        );
    }

    private String fetchEmailBodyFor(
        UserRegistrationConfirmation confirmation
    ) {
        String rawEmailBodyURL = System.getProperty(
            REGISTRATION_CONFIRMATION_MAIL_BODY_LOCATION
        );
        try {
            return replaceMacros(
                IOUtils.toString(
                    URI.create(rawEmailBodyURL),
                    System.getProperty(CHARSET_NAME)
                ),
                createMacrosFor(confirmation)
            );
        } catch (IOException e) {
            LOGGER.error(
                "Can not fetch email body specified by "
                    + REGISTRATION_CONFIRMATION_MAIL_BODY_LOCATION
                    + "="
                    + rawEmailBodyURL
            );
            throw new RuntimeException(e);
        }
    }

    private String fetchEmailBodyFrom(URI uri) {
        try {
            return IOUtils.toString(uri, System.getProperty(CHARSET_NAME));
        } catch (IOException e) {
            LOGGER.error("Can not fetch email body specified by " + uri);
            throw new RuntimeException(e);
        }
    }

    private String fetchEmailBodyFor(
        UserResetPasswordRequest userResetPasswordRequest
    ) {
        String rawEmailBodyURL = System.getProperty(
            RESET_PASSWORD_MAIL_BODY_LOCATION
        );
        return replaceMacros(
            fetchEmailBodyFrom(URI.create(rawEmailBodyURL)),
            createMacrosFor(userResetPasswordRequest)
        );
    }

    private String replaceMacros(String emailBody, Map<String, String> macros) {
        Set<String> macrosKeys = macros.keySet();
        while (macrosKeys.stream().anyMatch(emailBody::contains)) {
            for (String key : macrosKeys) {
                emailBody = emailBody.replace(key, macros.get(key));
            }
        }
        return emailBody;
    }

    private Map<String, String> createMacrosFor(
        UserRegistrationConfirmation confirmation
    ) {
        Map<String, String> emailBodyMacros = new HashMap<>();
        URL registrationConfirmationURL = createRegistrationActivationURLFor(
            confirmation
        );
        emailBodyMacros.put(
            "${CONFIRMATION_URL}",
            StringEscapeUtils.escapeHtml4(
                registrationConfirmationURL.toString()
            )
        );
        emailBodyMacros.put(
            "${HOST}", System.getProperty(FRONTEND_URL)
        );
        return emailBodyMacros;
    }

    private Map<String, String> createMacrosFor(
        UserResetPasswordRequest userResetPasswordRequest
    ) {
        Map<String, String> emailBodyMacros = new HashMap<>();
        try {
            emailBodyMacros.put(
                "${RESET_PASSWORD_URL}",
                new URL(getFrontendURL(), "/reset-password").toString()
            );
        } catch (MalformedURLException e) {
            LOGGER.error(e);
            throw new UncheckedIOException(e);
        }
        emailBodyMacros.put(
            "${RESET_PASSWORD_KEY}",
            userResetPasswordRequest.getResetKey().toString()
        );
        return emailBodyMacros;
    }

    private URL getFrontendURL() {
        try {
            return new URL(System.getProperty(FRONTEND_URL));
        } catch (MalformedURLException e) {
            LOGGER.error(e);
            throw new UncheckedIOException(e);
        }
    }

    private URL createRegistrationActivationURLFor(
        UserRegistrationConfirmation confirmation
    ) {
        URL frontendURL;
        URL registrationConfirmationURL;
        try {
            // todo replace with getFrontendURL
            frontendURL = new URL(System.getProperty(FRONTEND_URL));
            registrationConfirmationURL = new URL(
                frontendURL,
                "/search?key=" + URLEncoder.encode(
                    confirmation.getKey().toString(),
                    System.getProperty(CHARSET_NAME)
                )
            );
        } catch (MalformedURLException e) {
            LOGGER.error(
                "Can not create URL. Check the '" + FRONTEND_URL + "' property"
            );
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can not encode confirmation key");
            throw new RuntimeException(e);
        }
        return registrationConfirmationURL;
    }
}
