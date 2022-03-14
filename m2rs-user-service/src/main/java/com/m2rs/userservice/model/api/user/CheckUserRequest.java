package com.m2rs.userservice.model.api.user;

import com.m2rs.core.security.model.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckUserRequest {

    private final Long departmentId;
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String cellPhone;
    private final RoleType roleType;

    @Builder
    private CheckUserRequest(Long departmentId, String email, String password, String name,
        String phone, String cellPhone, RoleType roleType) {
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.roleType = roleType;
    }
}
