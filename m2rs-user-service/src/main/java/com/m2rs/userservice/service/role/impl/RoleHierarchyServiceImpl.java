package com.m2rs.userservice.service.role.impl;

import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.repository.role.RoleRepository;
import com.m2rs.userservice.service.role.RoleHierarchyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleRepository roleRepository;

    @Override
    public String generateRoleHierarchy() {

        List<Role> roles = roleRepository.findAll();

        StringBuilder concatRoles = new StringBuilder();

        for (Role role : roles) {
            if (role.getParentRole() != null) {
                concatRoles.append(role.getParentRole().getRolesName())
                    .append(" > ")
                    .append(role.getRolesName())
                    .append("\n");
            }
        }

        log.debug("role hierarchy={}", concatRoles);

        return concatRoles.toString();
    }
}
