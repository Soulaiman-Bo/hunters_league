package com.javangers.hunters_league.web.vm.mapper;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.domain.enumeration.Role;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.LoginResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    @Mapping(source = "email", target = "email")
    User toEntity(LoginRequestVM loginRequestVM);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    LoginResponseVM toResponseVM(User user);

    List<LoginResponseVM> toResponseVMList(List<User> users);
}