package com.m2rs.userservice.controller.company;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.commons.model.api.response.PageResponse;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.company.CreateCompanyRequest;
import com.m2rs.userservice.model.api.company.SearchCompanyRequest;
import com.m2rs.userservice.model.api.company.UpdateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.service.company.CompanyService;

import static com.m2rs.core.commons.model.api.response.ApiResult.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(path = "")
    public ApiResult<PageResponse<CompanyResponse>> searchCompany(
        SearchCompanyRequest searchCompanyRequest,
        @PageableDefault(value = 20, sort = "created_date", direction = Direction.DESC) Pageable pageable) {

        return OK(companyService.search(searchCompanyRequest, pageable));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public ApiResult<CompanyResponse> createCompany(
        @RequestBody CreateCompanyRequest createCompany) {
        return OK(companyService.createCompany(createCompany));
    }

    @PostMapping(path = "{companyId}/logo")
    public ApiResult<CompanyResponse> changeLogo(@PathVariable Long companyId,
        @RequestPart MultipartFile logo) {
        return OK(companyService.changeLogo(Id.of(Company.class, companyId), logo));
    }

    @PutMapping(path = "{companyId}")
    public ApiResult<CompanyResponse> changeCompany(@PathVariable long companyId,
        @RequestBody UpdateCompanyRequest updateCompanyRequest) {
        return OK(
            companyService.updateCompany(Id.of(Company.class, companyId),
                updateCompanyRequest));
    }

}
