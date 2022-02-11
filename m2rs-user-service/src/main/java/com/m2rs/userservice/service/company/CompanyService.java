package com.m2rs.userservice.service.company;

import com.m2rs.userservice.model.api.company.CompanyDto;
import com.m2rs.userservice.model.api.company.CreateCompany;

public interface CompanyService {

    CompanyDto createCompany(CreateCompany createCompany);

}
