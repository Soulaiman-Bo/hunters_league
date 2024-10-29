package com.javangers.hunters_league.web.rest;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.LoginService;
import com.javangers.hunters_league.service.dto.LoginRequestDTO;
import com.javangers.hunters_league.service.dto.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final User user;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}
