package com.javangers.hunters_league.service.dto;

import com.javangers.hunters_league.domain.enumeration.Role;
import lombok.Data;

@Data
public class LoginResponseDTO {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Role role;

    public LoginResponseDTO(String username, String firstName, String lastName, String email, Role role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }
}
