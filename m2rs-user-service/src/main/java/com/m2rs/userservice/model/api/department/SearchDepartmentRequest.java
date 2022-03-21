package com.m2rs.userservice.model.api.department;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchDepartmentRequest {

    private Long departmentId;
    private String name;

}
