package com.m2rs.userservice.model.api.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginRequest {

    private String email;
    private String password;

}
