package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class represents a user entity
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    private UserID id;

    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private String phoneNumber;

    private String password;

    private Set<UserStatus> userStatuses = new HashSet<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserID implements Serializable {

        private String email;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            UserID userID = (UserID) o;

            return email.equals(userID.email);
        }

        @Override
        public int hashCode() {
            return email.hashCode();
        }

        @Override
        public String toString() {
            return "UserID{" + "email='" + email + '\'' + '}';
        }
    }
}
