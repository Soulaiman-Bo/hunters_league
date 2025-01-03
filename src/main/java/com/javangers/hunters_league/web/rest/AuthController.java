package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.security.CustomUserDetails;
import com.javangers.hunters_league.security.CustomUserDetailsService;
import com.javangers.hunters_league.security.JwtTokenUtil;
import com.javangers.hunters_league.service.UserService;
import com.javangers.hunters_league.web.vm.*;
import com.javangers.hunters_league.web.vm.mapper.LoginMapper;
import com.javangers.hunters_league.web.vm.mapper.RegisterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService loginService;
    private final LoginMapper loginMapper;
    private final RegisterMapper registerMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestVM loginRequest) {
        Authentication  authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByEmail(loginRequest.getEmail()).getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserResponse userResponse = new UserResponse(
            userDetails.getUserId(),
            userDetails.getEmail(),
            userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()),
            userDetails.getFirstName(),
            userDetails.getLastName()
        );



        return ResponseEntity.ok(new LoginResponse(jwt, userResponse));

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestVM registerRequest) {
        User user = registerMapper.toEntity(registerRequest);
        User createdUser = loginService.register(user);
        UserResponseVM response = registerMapper.toResponseVM(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
