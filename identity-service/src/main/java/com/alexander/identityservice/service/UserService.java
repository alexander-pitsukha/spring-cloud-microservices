package com.alexander.identityservice.service;

import com.alexander.enums.RoleType;
import com.alexander.identityservice.controller.request.UserBody;
import com.alexander.identityservice.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    UserDto getUserByEmail(String email);

    RoleType getUserRole(Long userId);

    UserDto saveUser(UserBody userBody);

    void deleteUserById(Long userId);

}
