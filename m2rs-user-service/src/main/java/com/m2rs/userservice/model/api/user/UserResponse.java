package com.m2rs.userservice.model.api.user;


import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.core.security.model.RoleType;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String email;
    private final String name;

    private final List<RoleType> roleTypes;

    @Builder
    private UserResponse(Long id, String email, String name, List<RoleType> roleTypes) {
        this.id = id;
        this.email = email;
        this.name = name;

        this.roleTypes = defaultIfNull(roleTypes, Collections.emptyList());
    }
}
