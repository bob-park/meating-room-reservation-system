package com.m2rs.userservice.repository.user.query;

import com.m2rs.userservice.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    Page<User> search(UserSearchCondition condition, Pageable pageable);

}
