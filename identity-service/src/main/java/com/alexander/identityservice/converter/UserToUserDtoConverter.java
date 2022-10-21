package com.alexander.identityservice.converter;

import com.alexander.identityservice.dto.UserDto;
import com.alexander.identityservice.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setName(source.getName());
        userDto.setEmail(source.getEmail());
        userDto.setRoleType(source.getRole());
        return userDto;
    }

}
