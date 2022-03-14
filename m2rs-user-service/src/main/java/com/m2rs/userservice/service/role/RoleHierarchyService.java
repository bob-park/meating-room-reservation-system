package com.m2rs.userservice.service.role;

import java.util.List;
import java.util.Map;

public interface RoleHierarchyService {

    String generateRoleHierarchy();

    Map<String, List<String>> getRoleHierarchyMap();

}
