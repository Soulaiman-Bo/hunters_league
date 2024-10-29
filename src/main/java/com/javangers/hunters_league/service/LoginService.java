package com.javangers.hunters_league.service;

import com.javangers.hunters_league.service.dto.LoginRequestDTO;
import com.javangers.hunters_league.service.dto.LoginResponseDTO;

public interface LoginService {
    public LoginResponseDTO login(LoginRequestDTO loginRequest);
}
