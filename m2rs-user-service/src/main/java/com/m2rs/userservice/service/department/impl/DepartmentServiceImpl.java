package com.m2rs.userservice.service.department.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.m2rs.core.commons.exception.data.DataReferenceException;
import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.api.department.ModifyDepartmentRequest;
import com.m2rs.userservice.model.api.department.SearchDepartmentRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.company.CompanyRepository;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import com.m2rs.userservice.repository.department.query.SearchDepartmentQueryCondition;
import com.m2rs.userservice.service.department.DepartmentService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public DepartmentResponse createDepartment(Id<Company, Long> comId,
        CreateDepartmentRequest request) {

        checkNotNull(comId, "comId must be provided.");
        checkNotNull(request.getName(), "name must be provided.");

        Company company = companyRepository.findById(comId.value())
            .orElseThrow(() -> new NotFoundException(Company.class, comId.value()));

        Department department = Department.builder()
            .name(request.getName())
            .build();

        department.setCompany(company);

        Department savedDepartment = departmentRepository.save(department);

        return DepartmentResponse.builder()
            .id(savedDepartment.getId())
            .company(CompanyResponse.builder()
                .id(savedDepartment.getCompany().getId())
                .name(savedDepartment.getCompany().getName())
                .logoPath(savedDepartment.getCompany().getLogoPath())
                .createdDate(savedDepartment.getCompany().getCreatedDate())
                .lastModifiedDate(savedDepartment.getCompany().getLastModifiedDate())
                .build())
            .name(savedDepartment.getName())
            .createdDate(savedDepartment.getCreatedDate())
            .lastModifiedDate(savedDepartment.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public DepartmentResponse modifyDepartment(Id<Department, Long> departmentId,
        ModifyDepartmentRequest modifyRequest) {

        checkNotNull(departmentId, "departmentId must be provided.");

        Department department = departmentRepository.findById(departmentId.value())
            .orElseThrow(() -> new NotFoundException(Department.class, departmentId.value()));

        department.modify(modifyRequest);

        return DepartmentResponse.builder()
            .id(department.getId())
            .name(department.getName())
            .build();
    }

    @Override
    public DepartmentResponse getDepartment(Id<Company, Long> comId,
        Id<Department, Long> departmentId) {

        checkNotNull(comId, "comId must be provided.");
        checkNotNull(departmentId, "departmentId must be provided.");

        Department department = departmentRepository.getDepartment(comId, departmentId)
            .orElseThrow(() -> new NotFoundException(Department.class, departmentId.value()));

        Company company = department.getCompany();

        return DepartmentResponse.builder()
            .id(department.getId())
            .company(CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .logoPath(company.getLogoPath())
                .createdDate(company.getCreatedDate())
                .lastModifiedDate(company.getLastModifiedDate())
                .build())
            .name(department.getName())
            .createdDate(department.getCreatedDate())
            .lastModifiedDate(department.getLastModifiedDate())
            .build();
    }

    @Override
    public List<DepartmentResponse> search(Id<Company, Long> comId,
        SearchDepartmentRequest searchRequest) {

        checkNotNull(comId, "comId must be provided.");

        SearchDepartmentQueryCondition condition =
            searchRequest.getQueryCondition(comId.value());

        List<Department> result = departmentRepository.search(condition);

        return result.stream()
            .map(item -> DepartmentResponse.builder()
                .id(item.getId())
                .company(CompanyResponse.builder()
                    .id(item.getCompany().getId())
                    .name(item.getCompany().getName())
                    .logoPath(item.getCompany().getLogoPath())
                    .createdDate(item.getCompany().getCreatedDate())
                    .lastModifiedDate(item.getCompany().getLastModifiedDate())
                    .build())
                .name(item.getName())
                .createdDate(item.getCreatedDate())
                .lastModifiedDate(item.getLastModifiedDate())
                .build())
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DepartmentResponse removeDepartment(Id<Department, Long> departmentId) {

        checkNotNull(departmentId, "departmentId must be provided.");

        Department department = departmentRepository.findById(departmentId.value())
            .orElseThrow(() -> new NotFoundException(Department.class, departmentId.value()));

        if (!department.getUsers().isEmpty()) {
            throw new DataReferenceException(Department.class, User.class);
        }

        departmentRepository.delete(department);

        return DepartmentResponse.builder()
            .id(department.getId())
            .build();

    }
}
