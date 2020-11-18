package com.prabhu.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * This belongs to user-service
 *
 * @author Prabhu Madipalli
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private int id;

    @Column(unique = true, nullable = false)
    @Size(max = 25, min = 3)
    @NotNull
    @Pattern(regexp = "^[A-Za-z]*$")
    private String username;

    @Column(nullable = false)
    private String firstName;

    private String lastName;
    //Can be set only to Fixed values
    private String accessType;

    

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})")
    @Digits(integer = 10, fraction = 0)
    @Column(unique = true)
    private String mobileNumber;

    private boolean Confirmed;

    private String password;
}
