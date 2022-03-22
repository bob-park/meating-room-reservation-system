package com.m2rs.userservice.repository.department.query;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchDepartmentQueryCondition {


    private final Long comId;
    private final Long departmentId;
    private final String name;

    @Builder
    private SearchDepartmentQueryCondition(Long comId, Long departmentId, String name) {
        this.comId = comId;
        this.departmentId = departmentId;
        this.name = name;
    }
}
