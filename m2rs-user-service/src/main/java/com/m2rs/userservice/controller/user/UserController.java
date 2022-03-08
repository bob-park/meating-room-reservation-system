package com.m2rs.userservice.controller.user;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.userservice.model.api.user.CheckUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "check")
    public ApiResult<UserResponse> checkUser(@RequestBody CheckUserRequest checkUserRequest) {

        checkArgument(isNotEmpty(checkUserRequest.getEmail()), "email must be provided.");

        return ApiResult.ok(userService.getUser(checkUserRequest.getEmail()));

    }

}
