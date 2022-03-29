package com.m2rs.userservice.model.api.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ModifyUserRequest {

    private String password;
    private String name;
    private String phone;
    private String cellPhone;

    @Builder
    private ModifyUserRequest(String password, String name, String phone, String cellPhone) {
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cellPhone = cellPhone;
    }
}
