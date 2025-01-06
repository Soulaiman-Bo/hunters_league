package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    public User login(User user);
    public User register(User user);
    public User findUserByEmail(String email);
    Page<User> getAllUsers(int page, int size, String sortField, Sort.Direction sortDirection);
}
