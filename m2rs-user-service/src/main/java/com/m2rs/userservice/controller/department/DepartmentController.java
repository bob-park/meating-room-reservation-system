package com.m2rs.userservice.controller.department;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.service.department.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("company/{comId}/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping(path = "")
    public ApiResult<DepartmentResponse> createDepartment(@PathVariable Long comId,
        @RequestBody CreateDepartmentRequest request) {
        return ok(departmentService.createDepartment(Id.of(Company.class, comId), request));
    }

}
