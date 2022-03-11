package com.m2rs.userservice.security.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RestAuthentication {

    private final Long id;
    private final String email;
    private final String name;

    @Builder
    private RestAuthentication(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
