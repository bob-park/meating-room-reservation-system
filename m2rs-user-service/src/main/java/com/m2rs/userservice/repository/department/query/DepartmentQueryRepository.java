package com.m2rs.userservice.repository.department.query;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import java.util.List;
import java.util.Optional;

public interface DepartmentQueryRepository {

    List<Department> search(SearchDepartmentQueryCondition condition);

    Optional<Department> getDepartment(Id<Company, Long> comId, Id<Department, Long> departmentId);

}
