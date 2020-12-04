package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import io.javalin.core.security.Role;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a Hibernate POJO.
 * It represents a record in the pu_user_statuses table
 * A {@link UserStatus} class represents an entity of user role
 *
 * @see Role
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatus implements Serializable, Role {

    public static final String USER_STATUS_REGISTRATION_CONFIRMATION_PENDING =
        "REGISTRATION_ACTIVATION_PENDING";

    public static final String USER_STATUS_REGISTRATION_CONFIRMED            =
        "REGISTRATION_CONFIRMED";

    public static final String USER_STATUS_FREE_ACCOUNT                      =
        "FREE_ACCOUNT";

    public static final String USER_STATUS_PREMIUM_ACCOUNT                   =
        "PREMIUM_ACCOUNT";

    public static final String USER_STATUS_ADMIN_ACCOUNT                     =
        "ADMIN_ACCOUNT";

    public static final int FREE_ACCOUNT_STORED_STATISTIC_DAYS               =
        3;

    /**
     * A string representation of a status
     * */
    private String status;
}
