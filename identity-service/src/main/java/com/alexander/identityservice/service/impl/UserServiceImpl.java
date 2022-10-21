package com.alexander.identityservice.service.impl;

import com.alexander.enums.RoleType;
import com.alexander.identityservice.controller.request.UserBody;
import com.alexander.identityservice.dto.UserDto;
import com.alexander.identityservice.converter.UserToUserDtoConverter;
import com.alexander.identityservice.model.User;
import com.alexander.identityservice.repository.UserRepository;
import com.alexander.identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userToUserDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(final String email) {
        User user = userRepository.findByEmail(email);
        return userToUserDtoConverter.convert(user);
    }

    @Override
    public RoleType getUserRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getRole();
    }

    @Transactional
    @Override
    public UserDto saveUser(UserBody userBody) {
        User user = new User();
        user.setName(userBody.getName());
        user.setEmail(userBody.getEmail());
        user.setPassword(userBody.getPassword());
        user.setRole(userBody.getRole());
        user = userRepository.save(user);
        return userToUserDtoConverter.convert(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

}
