package com.m2rs.userservice.service.user;

import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.api.user.SearchUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse create(CreateUserRequest request);

    ServicePage<UserResponse> searchUser(Id<Company, Long> comId, SearchUserRequest condition,
        Pageable pageable);

    UserResponse getUser(Id<Company, Long> comId, Id<User, Long> id);

    UserResponse modifyUser(Id<User, Long> id, ModifyUserRequest modifyRequest);

    UserResponse removeUser(Id<User, Long> id);

    Boolean isExistEmail(Id<Company, Long> comId, String email);

}
