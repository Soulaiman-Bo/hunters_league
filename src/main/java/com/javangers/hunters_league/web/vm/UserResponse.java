package com.javangers.hunters_league.web.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private List<String> roles;
    private String firstName;
    private String lastName;
}
