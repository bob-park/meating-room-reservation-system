package com.m2rs.userservice.commons.security;

import com.m2rs.userservice.commons.security.annotation.WithMockCustomUser;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import com.m2rs.userservice.security.model.RestPrincipal;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
    WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        RestAuthenticationToken authentication = new RestAuthenticationToken(RestPrincipal.builder()
            .id(annotation.id())
            .comId(annotation.comId())
            .departmentId(annotation.departmentId())
            .email(annotation.email())
            .build(),
            null,
            Collections.singletonList(
                new SimpleGrantedAuthority(annotation.roleType().getRoleName())));

        context.setAuthentication(authentication);

        return context;
    }
}
