package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

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

    private String firstName;

    private String lastName;

    private String email;
}
