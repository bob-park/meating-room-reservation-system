package com.m2rs.userservice.security.voter;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ClassUtils.isAssignable;

import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.security.model.RestAuthentication;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import java.util.Collection;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ConnectionBasedVoter implements AccessDecisionVoter<FilterInvocation> {

    private static final SimpleGrantedAuthority GRANT_MANAGER =
        new SimpleGrantedAuthority(RoleType.ROLE_MANAGER.getRoleName());

    private final RequestMatcher requiresAuthorizationRequestMatcher;

    private final Function<String, Id<User, Long>> idExtractor;

    public ConnectionBasedVoter(
        RequestMatcher requiresAuthorizationRequestMatcher,
        Function<String, Id<User, Long>> idExtractor) {

        checkNotNull(requiresAuthorizationRequestMatcher,
            "requiresAuthorizationRequestMatcher must be provided.");
        checkNotNull(idExtractor, "idExtractor must be provided.");

        this.requiresAuthorizationRequestMatcher = requiresAuthorizationRequestMatcher;
        this.idExtractor = idExtractor;
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

        RestAuthentication jwtAuth = (RestAuthentication) authentication.getPrincipal();
        Id<User, Long> targetId = obtainTargetId(request);

        // 본인 자신 또는 MANAGER 권한인 경우
        if (jwtAuth.getId().equals(targetId.value())
            || authentication.getAuthorities().contains(GRANT_MANAGER)) {
            return ACCESS_GRANTED;
        }

        return ACCESS_DENIED;
    }

    private boolean requiresAuthorization(HttpServletRequest request) {
        return requiresAuthorizationRequestMatcher.matches(request);
    }

    private Id<User, Long> obtainTargetId(HttpServletRequest request) {
        return idExtractor.apply(request.getRequestURI());
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return isAssignable(FilterInvocation.class, clazz);
    }

}
