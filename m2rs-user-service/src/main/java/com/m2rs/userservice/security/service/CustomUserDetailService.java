package com.m2rs.userservice.security.service;

import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.UserRepository;
import com.m2rs.userservice.security.model.UserContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("userDetailService")
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
            .orElseThrow(() ->
                new UsernameNotFoundException(String.format("Not found email. (%s)", username)));

        Set<Role> userRoles = user.getUserRoles();

        List<SimpleGrantedAuthority> grantedAuthorities = userRoles.stream()
            .map(Role::getRolesName).collect(Collectors.toSet())
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());

        return new UserContext(user, grantedAuthorities);
    }
}
