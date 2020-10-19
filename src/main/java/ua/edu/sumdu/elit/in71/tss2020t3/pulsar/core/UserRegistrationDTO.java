package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a data container for a user creation request
 * (i.e registration)
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @JsonProperty
    private String email;

    @JsonProperty
    private int age;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty
    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty
    private String username;
}
