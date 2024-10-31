package com.javangers.hunters_league.web.vm;

import lombok.Data;

@Data
public class RegisterResponseVM {
    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String nationality;
    private String role;
}