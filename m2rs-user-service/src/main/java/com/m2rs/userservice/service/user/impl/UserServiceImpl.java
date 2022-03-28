package com.m2rs.userservice.service.user.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.exception.data.AlreadyExistDataException;
import com.m2rs.core.commons.model.api.response.Pagination;
import com.m2rs.core.commons.model.service.page.ServicePage;
import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.api.user.CreateUserRequest;
import com.m2rs.userservice.model.api.user.ModifyUserRequest;
import com.m2rs.userservice.model.api.user.SearchUserRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Company;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    @Override
    public UserResponse create(CreateUserRequest request) {

        checkNotNull(request.getDepartmentId(), "departmentId must be provided.");
        checkNotNull(request.getEmail(), "email must be provided.");
        checkNotNull(request.getPassword(), "password must be provided.");
        checkNotNull(request.getName(), "name must be provided.");

        Department department = departmentRepository.findById(request.getDepartmentId())
            .orElseThrow(() -> new NotFoundException(Department.class, request.getDepartmentId()));

        // 해당 email 이 존재하는지 확인
        boolean existEmail = userRepository.isExistEmail(
            Id.of(Company.class, department.getCompany().getId()),
            request.getEmail());

        if (existEmail) {
            throw new AlreadyExistDataException(User.class, request.getEmail());
        }

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
        createUser.setUserRoles(UserRoles.builder()
            .user(createUser)
            .role(role)
            .build());

        User savedUser = userRepository.save(createUser);

        return UserResponse.builder()
            .id(savedUser.getId())
            .email(savedUser.getEmail())
            .name(savedUser.getName())
            .roleTypes(Collections.singletonList(savedUser.getUserRoles()
                .getRole()
                .getRolesName()))
            .createdDate(savedUser.getCreatedDate())
            .lastModifiedDate(savedUser.getLastModifiedDate())
            .build();
    }

    @Override
    public ServicePage<UserResponse> searchUser(Id<Company, Long> comId,
        SearchUserRequest searchRequest, Pageable pageable) {

        checkNotNull(comId.value(), "comId must be provided.");

        Page<User> result =
            userRepository.search(searchRequest.getQueryCondition(comId.value()), pageable);

        return ServicePage.<UserResponse>builder()
            .contents(result.getContent().stream()
                .map(item -> UserResponse.builder()
                    .id(item.getId())
                    .email(item.getEmail())
                    .name(item.getName())
                    .roleTypes(Collections.singletonList(item.getUserRoles()
                        .getRole()
                        .getRolesName()))
                    .createdDate(item.getCreatedDate())
                    .lastModifiedDate(item.getLastModifiedDate())
                    .build())
                .collect(Collectors.toList()))
            .page(Pagination.builder()
                .totalCount(result.getTotalElements())
                .page(result.getNumber())
                .lastPage(result.getTotalPages())
                .build())
            .build();
    }

    @Override
    public UserResponse getUser(Id<Company, Long> comId, Id<User, Long> id) {

        User user = userRepository.getUser(comId, id)
            .orElseThrow(() -> new NotFoundException(User.class, id.value()));

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
            .phone(user.getPhone())
            .cellPhone(user.getCellPhone())
            .createdDate(user.getCreatedDate())
            .lastModifiedDate(user.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public UserResponse modifyUser(Id<User, Long> id, ModifyUserRequest modifyRequest) {

        checkArgument(isNotEmpty(id.value()), "id must be provided.");

        User user = userRepository.findById(id.value())
            .orElseThrow(() -> new NotFoundException(User.class, id.value()));

        if (isNotEmpty(modifyRequest.getPassword())) {
            user.modifyPassword(passwordEncoder.encode(modifyRequest.getPassword()));
        }

        user.modify(modifyRequest);

        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .phone(user.getPhone())
            .cellPhone(user.getCellPhone())
            .createdDate(user.getCreatedDate())
            .lastModifiedDate(user.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public UserResponse removeUser(Id<User, Long> id) {

        checkNotNull(id, "id must be providex.");

        User user = userRepository.findById(id.value())
            .orElseThrow(() -> new NotFoundException(User.class, id.value()));

        userRepository.delete(user);

        return UserResponse.builder()
            .id(user.getId())
            .build();
    }

    @Override
    public Boolean isExistEmail(Id<Company, Long> comId, String email) {

        checkNotNull(comId, "comId must be provided.");
        checkArgument(StringUtils.isNotBlank(email), "email must be provided.");

        return userRepository.isExistEmail(comId, email);
    }
}
