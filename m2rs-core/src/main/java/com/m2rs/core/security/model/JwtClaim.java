package com.m2rs.core.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtClaim {

    private final Long id;
    private final Long departmentId;
    private final String email;
    private final String name;
    private final RoleType roleType;

    @Builder
    private JwtClaim(Long id, Long departmentId, String email, String name,
        RoleType roleType) {
        this.id = id;
        this.departmentId = departmentId;
        this.email = email;
        this.name = name;
        this.roleType = roleType;
    }

    public Claims toClaims() {
        DefaultClaims claims = new DefaultClaims();

        claims.put("id", getId());
        claims.put("departmentId", getDepartmentId());
        claims.put("email", getEmail());
        claims.put("name", getName());
        claims.put("role", this.getRoleType());

        claims.setSubject(email);

        return claims;
    }

}
