package com.m2rs.userservice.model.api.department;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateDepartmentRequest {

    private final Long comId;
    private final String name;

    @Builder
    public CreateDepartmentRequest(Long comId, String name) {
        this.comId = comId;
        this.name = name;
    }
}
