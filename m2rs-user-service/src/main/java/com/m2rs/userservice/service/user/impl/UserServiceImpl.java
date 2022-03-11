package com.m2rs.userservice.service.user.impl;

import com.m2rs.userservice.exception.UserEmailNotFound;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.UserRepository;
import com.m2rs.userservice.service.user.UserService;
import java.util.stream.Collectors;
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
public class UserServiceImpl implements UserService {

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

        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .roleTypes(user.getUserRoles().stream()
                .map(Role::getRolesName)
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
}
