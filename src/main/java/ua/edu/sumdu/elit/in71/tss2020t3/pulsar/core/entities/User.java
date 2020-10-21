package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class represents a user entity
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private UserID id;

    private String username;

    private String firstName;

    private String lastName;

    private int age;

    private String phoneNumber;

    private String password;

    private Set<UserStatus> userStatuses;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (age != user.age) {
            return false;
        }
        if (!id.equals(user.id)) {
            return false;
        }
        if (!username.equals(user.username)) {
            return false;
        }
        if (!Objects.equals(firstName, user.firstName)) {
            return false;
        }
        if (!Objects.equals(lastName, user.lastName)) {
            return false;
        }
        if (!Objects.equals(phoneNumber, user.phoneNumber)) {
            return false;
        }
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        result =
            31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{"
            + "id=" + id
            + ", username='" + username + '\''
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", age=" + age
            + ", phoneNumber='" + phoneNumber + '\''
            + ", password='" + "********" + '\''
            + '}';
    }

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
