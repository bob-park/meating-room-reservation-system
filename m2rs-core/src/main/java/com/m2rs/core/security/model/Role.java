package com.m2rs.core.security.model;

import com.m2rs.core.commons.exception.NotFoundException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role findByName(String name) {
        return Arrays.stream(Role.values())
            .filter(role -> StringUtils.equalsIgnoreCase(name, role.getRoleName()))
            .findAny()
            .orElseThrow(() -> new NotFoundException("Role", name));
    }

    @Override
    public String toString() {
        return getRoleName();
    }
}
