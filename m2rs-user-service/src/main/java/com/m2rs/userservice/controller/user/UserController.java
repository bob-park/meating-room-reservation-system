package com.m2rs.userservice.controller.user;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.api.user.SearchUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("company/{comId}/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "")
    public ApiResult<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ok(userService.create(createUserRequest));
    }

    @GetMapping(path = "{userId}")
    public ApiResult<UserResponse> getUser(@PathVariable Long comId, @PathVariable Long userId) {
        return ok(userService.getUser(Id.of(Company.class, comId), Id.of(User.class, userId)));
    }

    @PutMapping(path = "{userId}")
    public ApiResult<UserResponse> updateUser(@PathVariable Long userId,
        @RequestBody ModifyUserRequest modifyRequest) {
        return ok(userService.modifyUser(Id.of(User.class, userId), modifyRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{userId}")
    public ApiResult<UserResponse> removeUser(@PathVariable Long userId) {
        return ok(userService.removeUser(Id.of(User.class, userId)));
    }

    @GetMapping(path = "list")
    public ApiResult<List<UserResponse>> getUserList(@PathVariable Long comId,
        SearchUserRequest userRequest,
        Pageable pageable) {

        ServicePage<UserResponse> userResponseServicePage =
            userService.searchUser(Id.of(Company.class, comId), userRequest, pageable);

        return ok(userResponseServicePage.getContents(), userResponseServicePage.getPage());

    }

    @GetMapping(path = "exist/email")
    public ApiResult<Boolean> existEmail(@PathVariable Long comId, @RequestParam String email) {
        return ok(userService.isExistEmail(Id.of(Company.class, comId), email));
    }

}
