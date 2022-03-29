package com.m2rs.userservice.model.api.user;

import com.m2rs.core.security.model.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {

    private Long departmentId;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String cellPhone;

    private RoleType role;

    @Builder
    private CreateUserRequest(Long departmentId, String email, String password, String name,
        String phone, String cellPhone, RoleType role) {
        this.departmentId = departmentId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.role = role;
    }
}
