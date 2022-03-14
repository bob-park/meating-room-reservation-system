package com.m2rs.userservice.model.api.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateUserRequest {

    private final Long departmentId;
    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String cellPhone;

}
