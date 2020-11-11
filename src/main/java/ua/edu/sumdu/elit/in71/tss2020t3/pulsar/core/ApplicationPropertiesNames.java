package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

/**
 * A container for static application properties names
 * */
public final class ApplicationPropertiesNames {

    // General properties
    /**
     * Represents a root directory for file-based log files
     * */
    public static final String LOG_DIRECTORY                                  =
        "pulsar.log.directory";

    /**
     * Represents a port which an application instance will use
     * */
    public static final String SERVER_PORT                                    =
        "pulsar.server.port";

    /**
     * Represents a timezone set on the database side by default
     * */
    public static final String DATABASE_TIMEZONE                              =
        "pulsar.database.timezone";

    /**
     * Represents a database connection URL
     * */
    public static final String DATABASE_URL                                   =
        "pulsar.database.connection.url";

    /**
     * Represents a username which will be used when connecting to a database
     * */
    public static final String DATABASE_USER                                  =
        "pulsar.database.connection.user";

    /**
     * Represents a password of a database user profile
     * which will be used when connecting to a database
     * */
    public static final String DATABASE_PASSWORD                              =
        "pulsar.database.connection.password";

    /**
     * Represents a JDBC connection driver class
     * which will be used when interacting with a database
     * */
    public static final String DATABASE_DRIVER_CLASS                          =
        "pulsar.database.connection.driver_class";

    /**
     * Represents a dialect class, which will be used by Hibernate framework
     * <p>
     * See more <a href="https://hibernate.org/orm/documentation">here</a>
     * */
    public static final String DATABASE_DIALECT                               =
        "pulsar.database.dialect";

    /**
     * Represents a regular expression that will be used for email validation
     * */
    public static final String EMAIL_REGEXP                                   =
        "pulsar.email.regexp";

    /**
     * Represents encoding name that will be used for file-related operations
     * */
    public static final String CHARSET_NAME                                   =
        "pulsar.charset.name";

    /**
     * Represents a frontend server {@link java.net.URL} which is
     * a 'view' part of MVC for this {@link Application}
     * */
    public static final String FRONTEND_URL                                   =
        "pulsar.frontend.url";

    /**
     * Represents a {@link java.net.URL} of this {@link Application}
     * */
    public static final String BACKEND_URL                                    =
        "pulsar.backend.url";

    // User properties
    /**
     * Represents a regular expression for a user phone number validation
     * */
    public static final String USER_PHONE_NUMBER_REGEXP                       =
        "pulsar.user.phone_number.regexp";

    /**
     * Represents a regular expression for a user password validation
     * */
    public static final String USER_PASSWORD_REGEXP                           =
        "pulsar.user.password.regexp";

    /**
     * Represents a first name maximum length
     * */
    public static final String USER_FIRST_NAME_MAX_LENGTH                     =
        "pulsar.user.firstname.length.max";

    /**
     * Represents a last name maximum length
     * */
    public static final String USER_LAST_NAME_MAX_LENGTH                      =
        "pulsar.user.lastname.length.max";

    /**
     * Represents a regular expression for a username validation
     * */
    public static final String USERNAME_REGEXP                                =
        "pulsar.user.username.regexp";

    /**
     * Represents a minimal allowed user age for registration
     * */
    public static final String USER_MIN_AGE                                   =
        "pulsar.user.age.min";

    // User statuses

    /**
     * Represents a role name that will be assigned to a newly-registered user
     * */
    public static final String USER_STATUS_REGISTRATION_CONFIRMATION_PENDING  =
        "pulsar.user.status.registration_confirmation_pending";

    /**
     * Represents a role name that will be assigned to a user
     * which has confirmed his/her registration
     * */
    public static final String USER_STATUS_REGISTRATION_CONFIRMED             =
        "pulsar.user.status.registration_confirmed";

    // Mail properties
    /**
     * A {@link Boolean} value indicating that an SMTP server (which is used
     * to send {@link javax.mail.Message}s) requires authenticated connection
     * */
    public static final String SMTP_AUTHENTICATION_ENABLED                    =
        "pulsar.mail.smtp.auth";

    /**
     * A {@link Boolean} value that enables the use
     * of the STARTTLS command (if supported by the server)
     * to switch the connection to a TLS-protected
     * connection before issuing any login commands to the SMTP server
     * */
    public static final String SMTP_STARTTLS_ENABLED                          =
        "pulsar.mail.smtp.starttls.enabled";

    /**
     * Represents a {@link java.net.URL} of an SMTP server to use
     * */
    public static final String SMTP_HOST                                      =
        "pulsar.mail.smtp.host";

    /**
     * Represents an SMTP server port
     * */
    public static final String SMTP_PORT                                      =
        "pulsar.mail.smtp.port";

    /**
     * Represents an user which will be used during interacting
     * with an SMTP server
     * */
    public static final String MAIL_USER                                      =
        "pulsar.mail.user";

    /**
     * Represents an password for {@link #MAIL_USER} account
     * */
    public static final String SMTP_MAIL_PASSWORD                             =
        "pulsar.mail.password";

    // Mail-related properties
    /**
     * Represents a subject for email which will be sent to user
     * for registration confirmation
     *
     * @see #USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
     * @see #USER_STATUS_REGISTRATION_CONFIRMED
     * @see #REGISTRATION_CONFIRMATION_MAIL_BODY_LOCATION
     * */
    public static final String REGISTRATION_CONFIRMATION_MAIL_SUBJECT         =
        "pulsar.user.registration.confirmation.mail.subject";

    public static final String RESET_PASSWORD_MAIL_SUBJECT                    =
        "pulsar.user.password.reset.mail.subject";

    /**
     * Represents a {@link java.net.URL} of an HTML file
     * which should be used as the body
     * for a user registration confirmation email
     *
     * @see #USER_STATUS_REGISTRATION_CONFIRMATION_PENDING
     * @see #USER_STATUS_REGISTRATION_CONFIRMED
     * @see #REGISTRATION_CONFIRMATION_MAIL_SUBJECT
     * */
    public static final String REGISTRATION_CONFIRMATION_MAIL_BODY_LOCATION   =
        "pulsar.user.registration.confirmation.mail.htmlbody.location";

    public static final String RESET_PASSWORD_MAIL_BODY_LOCATION              =
        "pulsar.user.password.reset.mail.htmlbody.location";

    // ClientHost properties
    /**
     * Represents a regular expression each
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost#getPublicKey()}
     * must fit to
     * */
    public static final String CLIENT_HOST_PUBLIC_KEY_REGEXP                  =
        "pulsar.client_host.public_key.regexp";

    /**
     * Represents a regular expression each
     * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.client.ClientHost#getName()}
     * must fit to
     * */
    public static final String CLIENT_HOST_NAME_REGEXP                        =
        "pulsar.client_host.name.regexp";

    public static final String KEYSTORE_PATH                                  =
        "pulsar.keystore.path";

    public static final String KEYSTORE_PASSWORD                              =
        "pulsar.keystore.password";


    private ApplicationPropertiesNames() {

    }
}
