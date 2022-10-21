package com.alexander.util;

import com.alexander.enums.RoleType;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class RoleUtil {

    public boolean isAdminRole(String role) {
        return Objects.equals(RoleType.ADMIN.name(), role);
    }

    public boolean isUserRole(String role) {
        return Objects.equals(RoleType.USER.name(), role);
    }

}
