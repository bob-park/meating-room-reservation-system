package com.m2rs.userservice.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m2rs.userservice.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
