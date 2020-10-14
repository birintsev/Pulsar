package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * This class represents a user entity
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private UserID id;

    private String firstName;

    private String lastName;

    private int age;

    private String phoneNumber;

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
        if (!firstName.equals(user.firstName)) {
            return false;
        }
        if (!lastName.equals(user.lastName)) {
            return false;
        }
        return Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + age;
        result = 31 * result
            + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
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
            return "UserID{" +
                "email='" + email + '\'' +
                '}';
        }
    }
}
