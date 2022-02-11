package com.m2rs.userservice.service.company;

import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyDto;
import com.m2rs.userservice.model.api.company.CreateCompany;
import com.m2rs.userservice.model.entity.Company;

public interface CompanyService {

    CompanyDto createCompany(CreateCompany createCompany);

    CompanyDto changeLogo(Id<Company, Long> id, MultipartFile logoFile);

}
