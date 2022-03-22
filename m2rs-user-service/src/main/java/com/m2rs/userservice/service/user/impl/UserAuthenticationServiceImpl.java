package com.m2rs.userservice.service.user.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.UserRepository;
import com.m2rs.userservice.service.user.UserAuthenticationService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException(String.format("Not found email. (%s)", email)));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Department department = user.getDepartment();
        Company company = isNotEmpty(department) ? department.getCompany() : null;

        return UserResponse.builder()
            .id(user.getId())
            .comId(isNotEmpty(company) ? company.getId() : null)
            .departmentId(isNotEmpty(department) ? department.getId() : null)
            .email(user.getEmail())
            .name(user.getName())
            .roleTypes(Collections.singletonList(user.getUserRoles()
                .getRole()
                .getRolesName()))
            .build();
    }
}
