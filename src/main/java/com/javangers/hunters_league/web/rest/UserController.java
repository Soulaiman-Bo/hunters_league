package com.javangers.hunters_league.web.rest;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.UserService;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.UserResponseVM;
import com.javangers.hunters_league.web.vm.RegisterRequestVM;
import com.javangers.hunters_league.web.vm.mapper.LoginMapper;
import com.javangers.hunters_league.web.vm.mapper.RegisterMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController {

    private final UserService loginService;
    private final LoginMapper loginMapper;
    private final RegisterMapper registerMapper;

    @PostMapping("/login")
    public ResponseEntity<UserResponseVM> login(@Valid @RequestBody LoginRequestVM loginRequest) {
        User user = loginMapper.toEntity(loginRequest);
        User returnedUser = loginService.login(user);
        UserResponseVM response = loginMapper.toResponseVM(returnedUser);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestVM registerRequest) {
            User user = registerMapper.toEntity(registerRequest);
            User createdUser = loginService.register(user);
            UserResponseVM response = registerMapper.toResponseVM(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Admin Only
    @GetMapping("/search")
    public ResponseEntity<UserResponseVM> search(
            @RequestParam
            @Email(message = "Invalid email format")  // Spring validation
            @NotBlank(message = "Email is required")
            String email
    ) {
        User returnedUser = loginService.findUserByEmail(email);
        UserResponseVM response = loginMapper.toResponseVM(returnedUser);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<UserResponseVM>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String sort // Format: "field,direction" (e.g., "email,desc")
    ) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.fromString(sortParams[1]);

        Page<User> users = loginService.getAllUsers(page, size, sortField, sortDirection);
        Page<UserResponseVM> response = users.map(loginMapper::toResponseVM);
        return ResponseEntity.ok(response);
    }

}
