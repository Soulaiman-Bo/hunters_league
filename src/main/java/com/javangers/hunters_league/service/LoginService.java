package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.LoginResponseVM;

public interface LoginService {
    public User login(User user);
    public User register(User user);
}
