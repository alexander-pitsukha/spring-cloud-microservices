package com.alexander.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum RoleType implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER");

    private final String authority;

}
