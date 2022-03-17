package com.m2rs.userservice.repository.user.query;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSearchCondition {

    private final Long id;
    private final Long departmentId;
    private final String email;
    private final String name;

    @Builder
    private UserSearchCondition(Long id, Long departmentId, String email, String name) {
        this.id = id;
        this.departmentId = departmentId;
        this.email = email;
        this.name = name;
    }
}
