package com.m2rs.userservice.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.query.CompanyQueryRepository;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyQueryRepository {

}
