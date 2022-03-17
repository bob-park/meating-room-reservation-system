package com.m2rs.userservice.service.user;

import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.query.UserSearchCondition;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse login(String email, String password);

    UserResponse getUser(String email);

    UserResponse create(CreateUserRequest request);

    ServicePage<UserResponse> searchUser(UserSearchCondition condition, Pageable pageable);

    UserResponse getUser(Id<User, Long> id);

    UserResponse modifyUser(Id<User, Long> id, ModifyUserRequest modifyRequest);

}
