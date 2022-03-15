package com.m2rs.userservice.service.company;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.company.CreateCompanyRequest;
import com.m2rs.userservice.model.api.company.SearchCompanyRequest;
import com.m2rs.userservice.model.api.company.UpdateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;

public interface CompanyService {

    CompanyResponse getCompany(Id<Company, Long> id);

    ServicePage<CompanyResponse> search(SearchCompanyRequest searchCompanyRequest, Pageable pageable);

    CompanyResponse createCompany(CreateCompanyRequest createCompany);

    CompanyResponse changeLogo(Id<Company, Long> id, MultipartFile logoFile);

    CompanyResponse updateCompany(Id<Company, Long> id, UpdateCompanyRequest updateCompanyRequest);

}
