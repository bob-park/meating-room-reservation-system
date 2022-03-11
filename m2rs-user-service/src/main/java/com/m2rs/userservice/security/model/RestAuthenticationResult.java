package com.m2rs.userservice.security.model;

import com.m2rs.userservice.model.api.user.UserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestAuthenticationResult {

    private final String token;
    private final UserResponse user;

}
