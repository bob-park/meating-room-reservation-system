package com.m2rs.userservice.model.api.department;

import com.m2rs.userservice.model.api.company.CompanyResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DepartmentResponse {

    private final Long id;
    private final CompanyResponse company;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private DepartmentResponse(Long id,
        CompanyResponse company, String name,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
