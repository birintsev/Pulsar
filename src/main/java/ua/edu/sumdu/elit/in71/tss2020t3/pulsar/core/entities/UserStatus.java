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

    /**
     * A string representation of a status
     * */
    private String status;
}
