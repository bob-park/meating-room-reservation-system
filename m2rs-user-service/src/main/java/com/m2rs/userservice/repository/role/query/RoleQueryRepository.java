package com.m2rs.userservice.repository.role.query;

import com.m2rs.userservice.model.entity.Role;
import java.util.List;

public interface RoleQueryRepository {

    List<Role> searchAll();

}
