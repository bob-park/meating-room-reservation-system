package com.m2rs.userservice.service.company.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.m2rs.userservice.model.api.company.CompanyDto;
import com.m2rs.userservice.model.api.company.CreateCompany;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.service.company.CompanyService;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public CompanyDto createCompany(CreateCompany createCompany) {

        checkArgument(isNotEmpty(createCompany.getName()), "name must be provided.");

        Company company = Company.builder()
            .name(createCompany.getName())
            .build();

        company = companyRepository.save(company);

        return CompanyDto.builder()
            .id(company.getId())
            .name(company.getName())
            .createdDate(company.getCreatedDate())
            .lastModifiedDate(company.getLastModifiedDate())
            .build();
    }

}
