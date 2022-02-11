package com.m2rs.userservice.model.api.company;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyDto {

    private final Long id;
    private final String name;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private CompanyDto(Long id, String name, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
