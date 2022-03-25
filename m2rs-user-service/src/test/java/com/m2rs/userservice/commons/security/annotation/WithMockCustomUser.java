package com.m2rs.userservice.commons.security.annotation;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.commons.security.WithMockCustomUserSecurityContextFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long id() default 1;

    long comId() default 0;

    long departmentId() default 0;

    String email();

    RoleType roleType() default RoleType.ROLE_USER;

}
