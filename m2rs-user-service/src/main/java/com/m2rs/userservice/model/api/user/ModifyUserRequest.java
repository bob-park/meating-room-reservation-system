package com.m2rs.userservice.model.api.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ModifyUserRequest {

    private final String password;
    private final String name;
    private final String phone;
    private final String cellPhone;

    @Builder
    private ModifyUserRequest(String password, String name, String phone, String cellPhone) {
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
    }
}
