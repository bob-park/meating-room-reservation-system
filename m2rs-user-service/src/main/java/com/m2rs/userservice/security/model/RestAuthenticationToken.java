package com.m2rs.userservice.security.model;

import com.m2rs.userservice.model.api.user.UserLoginRequest;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

public class RestAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private String credentials;

    public RestAuthenticationToken(Object principal, String credentials) {
        super(null);

        this.principal = principal;
        this.credentials = credentials;
    }

    public RestAuthenticationToken(Object principal, String credentials,
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        this.principal = principal;
        this.credentials = credentials;

        super.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
            "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    public UserLoginRequest getRequest() {
        UserLoginRequest userLoginRequest = new UserLoginRequest();

        userLoginRequest.setEmail(String.valueOf(getPrincipal()));
        userLoginRequest.setPassword(getCredentials());

        return userLoginRequest;
    }
}
