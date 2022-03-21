package com.m2rs.userservice.service.department;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;
import com.m2rs.userservice.model.entity.Company;

public interface DepartmentService {

    DepartmentResponse createDepartment(Id<Company, Long> comId, CreateDepartmentRequest request);


}
