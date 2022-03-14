package com.m2rs.userservice.service.user.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.google.common.base.Preconditions;
import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.exception.UserEmailNotFound;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.model.entity.UserRoles;
import com.m2rs.userservice.repository.department.DepartmentRepository;
import com.m2rs.userservice.repository.role.RoleRepository;
import com.m2rs.userservice.repository.user.UserRepository;
import com.m2rs.userservice.service.user.UserService;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException(String.format("Not found email. (%s)", email)));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .roleTypes(user.getUserRoles().stream()
                .map(userRoles -> userRoles.getRole().getRolesName())
                .collect(Collectors.toList()))
            .build();
    }

    @Override
    public UserResponse getUser(String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserEmailNotFound(email));

        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .build();
    }

    @Transactional
    @Override
    public UserResponse create(CreateUserRequest request) {

        checkArgument(isNotEmpty(request.getDepartmentId()), "departmentId must be provided.");
        checkArgument(isNotEmpty(request.getEmail()), "email must be provided.");
        checkArgument(isNotEmpty(request.getPassword()), "password must be provided.");
        checkArgument(isNotEmpty(request.getName()), "name must be provided.");

        Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() -> new NotFoundException(Department.class, request.getDepartmentId()));

        RoleType requestRole = defaultIfNull(request.getRole(), RoleType.ROLE_USER);

        Role role = roleRepository.findByRolesName(requestRole)
            .orElseThrow(() -> new NotFoundException(Role.class, requestRole));

        User createUser = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .phone(request.getPhone())
            .cellPhone(request.getCellPhone())
            .build();

        createUser.setDepartment(department);

        // TODO 추후 나중에 다중 권한을 적용해보자
        createUser.addRole(UserRoles.builder()
            .user(createUser)
            .role(role)
            .build());

        User savedUser = userRepository.save(createUser);

        return UserResponse.builder()
            .id(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getName())
            .roleTypes(savedUser.getUserRoles().stream()
                .map(userRoles -> userRoles.getRole().getRolesName())
                .collect(Collectors.toList()))
            .build();
    }
}
