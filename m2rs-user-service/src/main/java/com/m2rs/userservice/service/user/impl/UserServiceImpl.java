package com.m2rs.userservice.service.user.impl;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.userservice.exception.UserEmailNotFound;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.UserRepository;
import com.m2rs.userservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUser(String email) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserEmailNotFound(email));

        Company department = user.getDepartment();

        return UserResponse.builder()
            .id(user.getId())
            .departmentId(isNotEmpty(department) ? department.getId() : null)
            .departmentName(isNotEmpty(department) ? department.getName() : null)
            .email(user.getEmail())
            .encPassword(user.getPassword())
            .name(user.getName())
            .phone(user.getPhone())
            .cellPhone(user.getCellPhone())
            .build();
    }
}
