package com.m2rs.userservice.repository.company.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.m2rs.userservice.model.entity.Company;

public interface CompanyQueryRepository {

    Page<Company> search(SearchCompanyQueryCondition condition, Pageable pageable);

}
