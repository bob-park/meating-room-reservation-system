package com.m2rs.userservice.controller.admin.company;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.company.CompanyResponse;
import com.m2rs.userservice.model.api.company.CreateCompanyRequest;
import com.m2rs.userservice.model.api.company.SearchCompanyRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.service.company.CompanyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/company")
public class AdminCompanyController {

    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public ApiResult<CompanyResponse> createCompany(
        @RequestBody CreateCompanyRequest createCompany) {
        return ok(companyService.createCompany(createCompany));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{comId}")
    public ApiResult<CompanyResponse> remove(@PathVariable Long comId) {
        return ok(companyService.removeCompany(Id.of(Company.class, comId)));
    }

    @GetMapping(path = "list")
    public ApiResult<List<CompanyResponse>> searchCompany(
        SearchCompanyRequest searchCompanyRequest,
        @PageableDefault(value = 20, sort = "created_date", direction = Direction.DESC) Pageable pageable) {

        ServicePage<CompanyResponse> result = companyService.search(searchCompanyRequest, pageable);

        return ok(result.getContents(), result.getPage());
    }

}
