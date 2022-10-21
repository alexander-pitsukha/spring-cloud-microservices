package com.alexander.identityservice.dto;

import com.alexander.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private RoleType roleType;

}
