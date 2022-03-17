package com.m2rs.userservice.service.user;

import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.repository.user.query.UserSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse login(String email, String password);

    UserResponse getUser(String email);

    UserResponse create(CreateUserRequest request);

    ServicePage<UserResponse> searchUser(UserSearchCondition condition, Pageable pageable);

}
