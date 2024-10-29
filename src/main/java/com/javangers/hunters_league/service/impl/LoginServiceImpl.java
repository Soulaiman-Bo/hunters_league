package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.repository.UserRepository;
import com.javangers.hunters_league.service.LoginService;
import com.javangers.hunters_league.service.dto.LoginRequestDTO;
import com.javangers.hunters_league.service.dto.LoginResponseDTO;
import com.javangers.hunters_league.web.errors.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + loginRequest.getEmail()));

        return new LoginResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
    }
}