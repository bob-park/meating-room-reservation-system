package com.m2rs.userservice.model.api.department;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DepartmentResponse {

    private final Long id;
    private final String name;

    @Builder
    private DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
