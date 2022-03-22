package com.m2rs.userservice.controller.company;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.company.UpdateCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(path = "{companyId}")
    public ApiResult<CompanyResponse> getCompany(@PathVariable Long companyId) {
        return ok(companyService.getCompany(Id.of(Company.class, companyId)));
    }

    @PostMapping(path = "{companyId}/logo")
    public ApiResult<CompanyResponse> changeLogo(@PathVariable Long companyId,
        @RequestPart MultipartFile logo) {
        return ok(companyService.changeLogo(Id.of(Company.class, companyId), logo));
    }

    @PutMapping(path = "{companyId}")
    public ApiResult<CompanyResponse> changeCompany(@PathVariable long companyId,
        @RequestBody UpdateCompanyRequest updateCompanyRequest) {
        return ok(
            companyService.updateCompany(Id.of(Company.class, companyId),
                updateCompanyRequest));
    }

}
