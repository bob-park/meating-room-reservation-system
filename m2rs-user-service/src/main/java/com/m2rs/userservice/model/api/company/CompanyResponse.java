package com.m2rs.userservice.model.api.company;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyResponse {

    private final Long id;
    private final String name;
    private final String logoPath;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private CompanyResponse(Long id, String name, String logoPath, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.logoPath = logoPath;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
