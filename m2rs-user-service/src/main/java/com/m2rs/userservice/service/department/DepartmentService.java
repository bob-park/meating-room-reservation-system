package com.m2rs.userservice.service.department;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.api.department.ModifyDepartmentRequest;
import com.m2rs.userservice.model.api.department.SearchDepartmentRequest;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(Id<Company, Long> comId, CreateDepartmentRequest request);

    DepartmentResponse modifyDepartment(Id<Department, Long> departmentId,
        ModifyDepartmentRequest modifyRequest);

    DepartmentResponse getDepartment(Id<Company, Long> comId, Id<Department, Long> departmentId);

    List<DepartmentResponse> search(Id<Company, Long> comId, SearchDepartmentRequest searchRequest);
}
