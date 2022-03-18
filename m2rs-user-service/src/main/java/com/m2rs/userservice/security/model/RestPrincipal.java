package com.m2rs.userservice.security.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RestPrincipal {

    private final Long id;
    private final Long comId;
    private final Long departmentId;
    private final String email;
    private final String name;

    @Builder
    private RestPrincipal(Long id, Long comId, Long departmentId, String email,
        String name) {
        this.id = id;
        this.comId = comId;
        this.departmentId = departmentId;
        this.email = email;
        this.name = name;
    }
}
