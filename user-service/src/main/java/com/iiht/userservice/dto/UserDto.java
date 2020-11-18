package com.iiht.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    @NotNull
    private String username;

    private String firstName;

    private String lastName;

    private String accessType;

    private String email;

    private String mobileNumber;

    private boolean Confirmed;
}
