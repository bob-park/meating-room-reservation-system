package com.m2rs.userservice.service.department.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.google.common.base.Preconditions;
import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import com.m2rs.userservice.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        checkArgument(isNotEmpty(request.getComId()), "comId must be provided.");
        checkArgument(isNotEmpty(request.getName()), "name must be provided.");

        Company company = companyRepository.findById(request.getComId())
            .orElseThrow(() -> new NotFoundException(Company.class, request.getComId()));

        Department department = Department.builder()
            .name(request.getName())
            .build();

        department.setCompany(company);

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentResponse.builder()
            .id(savedDepartment.getId())
            .name(savedDepartment.getName())
            .build();
    }
}
