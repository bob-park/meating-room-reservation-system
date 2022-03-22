package com.m2rs.userservice.model.api.department;

import com.m2rs.userservice.repository.department.query.SearchDepartmentQueryCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchDepartmentRequest {

    private Long departmentId;
    private String name;

    public SearchDepartmentQueryCondition getQueryCondition(Long comId) {
        return SearchDepartmentQueryCondition.builder()
            .comId(comId)
            .departmentId(departmentId)
            .name(name)
            .build();
    }

}
