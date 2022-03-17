package com.m2rs.userservice.repository.user.query.impl;

import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.query.UserQueryRepository;
import com.m2rs.userservice.repository.user.query.UserSearchCondition;
import org.springframework.data.domain.Page;

public class UserQueryRepositoryImpl implements UserQueryRepository {

    @Override
    public Page<User> search(UserSearchCondition condition) {
        return null;
    }
}
