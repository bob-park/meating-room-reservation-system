package com.m2rs.meetingroomservice.feign.client;

import com.m2rs.meetingroomservice.model.api.company.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @GetMapping(path = "company/{companyId}")
    CompanyResponse getCompany(@PathVariable Long companyId);


}
