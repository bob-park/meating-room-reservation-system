package com.m2rs.userservice.service.user;

import com.m2rs.userservice.model.api.user.UserResponse;

public interface UserService {

    UserResponse login(String email, String password);

    UserResponse getUser(String email);

}
