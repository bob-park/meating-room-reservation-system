package com.m2rs.userservice.service.department;

import com.m2rs.userservice.model.api.department.CreateDepartmentRequest;
import com.m2rs.userservice.model.api.department.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(CreateDepartmentRequest request);


}
