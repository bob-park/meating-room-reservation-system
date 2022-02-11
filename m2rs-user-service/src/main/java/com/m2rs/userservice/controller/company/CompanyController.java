package com.m2rs.userservice.controller.company;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyDto;
import com.m2rs.userservice.model.api.company.CreateCompany;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.service.company.CompanyService;

import static com.m2rs.core.commons.model.api.response.ApiResult.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping(path = "")
    public ApiResult<CompanyDto> createCompany(@RequestBody CreateCompany createCompany) {
        return OK(companyService.createCompany(createCompany));
    }

    @PostMapping(path = "{companyId}/logo")
    public ApiResult<CompanyDto> changeLogo(@PathVariable Long companyId,
        @RequestPart MultipartFile logo) {
        return OK(companyService.changeLogo(Id.of(Company.class, companyId), logo));
    }

}
