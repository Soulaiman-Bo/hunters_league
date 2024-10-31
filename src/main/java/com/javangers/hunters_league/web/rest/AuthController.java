package com.javangers.hunters_league.web.rest;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.LoginService;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.LoginResponseVM;
import com.javangers.hunters_league.web.vm.RegisterRequestVM;
import com.javangers.hunters_league.web.vm.RegisterResponseVM;
import com.javangers.hunters_league.web.vm.mapper.LoginMapper;
import com.javangers.hunters_league.web.vm.mapper.RegisterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class AuthController {

    private final LoginService loginService;
    private final User user;
    private final LoginMapper loginMapper;
    private final RegisterMapper registerMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseVM> login(@Valid @RequestBody LoginRequestVM loginRequest) {
        User user = loginMapper.toEntity(loginRequest);
        User returnedUser = loginService.login(user);
        LoginResponseVM response = loginMapper.toResponseVM(returnedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestVM registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

            User user = registerMapper.toEntity(registerRequest);
            User createdUser = loginService.register(user);
            RegisterResponseVM response = registerMapper.toResponseVM(createdUser);
            return ResponseEntity.ok(response);

    }
}
