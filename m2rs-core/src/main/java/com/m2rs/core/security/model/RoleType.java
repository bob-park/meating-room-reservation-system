package com.m2rs.core.security.model;

import com.m2rs.core.commons.exception.NotFoundException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum RoleType {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_USER("ROLE_USER");

    private final String roleName;

    RoleType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static RoleType findByName(String name) {
        return Arrays.stream(RoleType.values())
            .filter(roleType -> StringUtils.equalsIgnoreCase(name, roleType.getRoleName()))
            .findAny()
            .orElseThrow(() -> new NotFoundException("Role", name));
    }

    @Override
    public String toString() {
        return getRoleName();
    }
}
