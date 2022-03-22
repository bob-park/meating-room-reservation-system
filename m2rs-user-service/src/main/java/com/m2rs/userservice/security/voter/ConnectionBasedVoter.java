package com.m2rs.userservice.security.voter;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ClassUtils.isAssignable;

import com.m2rs.userservice.security.model.RestAuthenticationToken;
import com.m2rs.userservice.security.voter.func.ConnectionAccessFunctional;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ConnectionBasedVoter implements AccessDecisionVoter<FilterInvocation> {

    private final RequestMatcher requiresAuthorizationRequestMatcher;
    private final ConnectionAccessFunctional accessFunc;

    public ConnectionBasedVoter(
        RequestMatcher requiresAuthorizationRequestMatcher,
        ConnectionAccessFunctional accessFunc) {

        checkNotNull(requiresAuthorizationRequestMatcher,
            "requiresAuthorizationRequestMatcher must be provided.");
        checkNotNull(accessFunc, "accessFunc must be provided.");

        this.requiresAuthorizationRequestMatcher = requiresAuthorizationRequestMatcher;
        this.accessFunc = accessFunc;
    }

    @Override
    public int vote(
        Authentication authentication, FilterInvocation fi,
        Collection<ConfigAttribute> attributes) {

        HttpServletRequest request = fi.getRequest();

        if (!requiresAuthorization(request)) {
            return ACCESS_GRANTED;
        }

        if (!isAssignable(RestAuthenticationToken.class, authentication.getClass())) {
            return ACCESS_ABSTAIN;
        }

        if (accessFunc.access(request.getRequestURI(), authentication)) {
            return ACCESS_GRANTED;
        }

        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return isAssignable(FilterInvocation.class, clazz);
    }

    private boolean requiresAuthorization(HttpServletRequest request) {
        return requiresAuthorizationRequestMatcher.matches(request);
    }

}
