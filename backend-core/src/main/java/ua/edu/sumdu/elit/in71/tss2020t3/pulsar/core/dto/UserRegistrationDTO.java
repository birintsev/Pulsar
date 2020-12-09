package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto;

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

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private int age;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty(required = true)
    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty(required = true)
    private String username;
}
