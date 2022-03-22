package com.m2rs.userservice.repository.department;

import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.repository.department.query.DepartmentQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>,
    DepartmentQueryRepository {

}
