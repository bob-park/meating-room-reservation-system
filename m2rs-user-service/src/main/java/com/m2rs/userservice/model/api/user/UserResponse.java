package com.m2rs.userservice.model.api.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final Long departmentId;
    private final String departmentName;
    private final String email;
    private final String encPassword;
    private final String name;
    private final String phone;
    private final String cellPhone;

    @Builder
    private UserResponse(Long id, Long departmentId, String departmentName, String email,
        String encPassword, String name, String phone, String cellPhone) {
        this.id = id;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.email = email;
        this.encPassword = encPassword;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
    }
}
