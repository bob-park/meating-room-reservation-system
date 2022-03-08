package com.m2rs.core.security.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtClaim {

    private final Long id;
    private final String departmentId;
    private final String email;
    private final String name;
    private final Role role;


    public Claims toClaims() {
        DefaultClaims claims = new DefaultClaims();

        claims.put("id", getId());
        claims.put("departmentId", getDepartmentId());
        claims.put("email", getEmail());
        claims.put("name", getName());
        claims.put("role", getRole());

        claims.setSubject(email);

        return claims;
    }

}
