package com.m2rs.userservice.controller.user;

import static com.google.common.base.Preconditions.checkArgument;
import static com.m2rs.core.commons.model.api.response.ApiResult.ok;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.repository.user.query.UserSearchCondition;
import com.m2rs.userservice.security.model.RestAuthentication;
import com.m2rs.userservice.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @PostMapping(path = "")
    public ApiResult<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ok(userService.create(createUserRequest));
    }

    @GetMapping(path = "check")
    public ApiResult<UserResponse> checkUser(
        @AuthenticationPrincipal RestAuthentication authentication) {

        checkArgument(isNotEmpty(authentication.getEmail()), "email must be provided.");

        return ok(userService.getUser(authentication.getEmail()));

    }

    @GetMapping(path = "list")
    public ApiResult<List<UserResponse>> getUserList(UserSearchCondition condition,
        Pageable pageable) {

        ServicePage<UserResponse> userResponseServicePage =
            userService.searchUser(condition, pageable);

        return ok(userResponseServicePage.getContents(), userResponseServicePage.getPage());

    }

}
