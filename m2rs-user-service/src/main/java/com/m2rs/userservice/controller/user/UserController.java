package com.m2rs.userservice.controller.user;

import static com.google.common.base.Preconditions.checkArgument;
import static com.m2rs.core.commons.model.api.response.ApiResult.ok;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.security.model.RestAuthentication;
import com.m2rs.userservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping(path = "check")
    public ApiResult<UserResponse> checkUser(
        @AuthenticationPrincipal RestAuthentication authentication) {

        checkArgument(isNotEmpty(authentication.getEmail()), "email must be provided.");

        return ok(userService.getUser(authentication.getEmail()));

    }

    @PostMapping(path = "")
    public ApiResult<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ok(userService.create(createUserRequest));
    }

}
