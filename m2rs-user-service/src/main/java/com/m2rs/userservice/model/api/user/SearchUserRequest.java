package com.m2rs.userservice.model.api.user;

import com.m2rs.userservice.repository.user.query.SearchUserQueryCondition;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchUserRequest {

    private final Long departmentId;
    private final String email;
    private final String name;

    @Builder
    private SearchUserRequest(Long departmentId, String email, String name) {
        this.departmentId = departmentId;
        this.email = email;
        this.name = name;
    }

    public SearchUserQueryCondition getQueryCondition(Long comId) {
        return SearchUserQueryCondition.builder()
            .comId(comId)
            .departmentId(departmentId)
            .email(email)
            .name(name)
            .build();
    }
}
