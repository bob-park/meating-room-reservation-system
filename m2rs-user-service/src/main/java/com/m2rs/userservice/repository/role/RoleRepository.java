package com.m2rs.userservice.repository.role;

import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.repository.role.query.RoleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleQueryRepository {


}
