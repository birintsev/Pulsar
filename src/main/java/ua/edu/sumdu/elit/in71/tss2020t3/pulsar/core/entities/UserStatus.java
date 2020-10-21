package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is a Hibernate POJO.
 * It represents a record in the pu_user_statuses table
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatus implements Serializable {

    public static final String REGISTRATION_CONFIRMATION_PENDING =
        System.getProperty(
            "pulsar.user.status.registration_confirmation_pending"
        );

    public static final String REGISTRATION_CONFIRMED =
        System.getProperty("pulsar.user.status.registration_confirmed");

    /**
     * A string representation of a status
     * */
    private String status;
}
