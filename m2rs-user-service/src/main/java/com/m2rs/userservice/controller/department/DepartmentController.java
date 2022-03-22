package com.m2rs.userservice.controller.department;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.api.department.ModifyDepartmentRequest;
import com.m2rs.userservice.model.api.department.SearchDepartmentRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.service.department.DepartmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("company/{comId}/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public ApiResult<DepartmentResponse> createDepartment(@PathVariable Long comId,
        @RequestBody CreateDepartmentRequest request) {
        return ok(departmentService.createDepartment(Id.of(Company.class, comId), request));
    }

    @GetMapping(path = "{departmentId}")
    public ApiResult<DepartmentResponse> getDepartment(@PathVariable Long comId,
        @PathVariable Long departmentId) {
        return ok(departmentService.getDepartment(Id.of(Company.class, comId),
            Id.of(Department.class, departmentId)));
    }

    @PutMapping(path = "{departmentId}")
    public ApiResult<DepartmentResponse> modifyDepartment(@PathVariable Long departmentId,
        @RequestBody ModifyDepartmentRequest modifyRequest) {
        return ok(departmentService.modifyDepartment(Id.of(Department.class, departmentId),
            modifyRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{departmentId}")
    public ApiResult<DepartmentResponse> removeDepartment(@PathVariable Long departmentId) {
        return ok(departmentService.removeDepartment(Id.of(Department.class, departmentId)));
    }

    @GetMapping(path = "list")
    public ApiResult<List<DepartmentResponse>> getList(@PathVariable Long comId,
        SearchDepartmentRequest searchRequest) {
        return ok(departmentService.search(Id.of(Company.class, comId), searchRequest));
    }
}
