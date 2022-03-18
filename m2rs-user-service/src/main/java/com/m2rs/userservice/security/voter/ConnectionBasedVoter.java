package com.m2rs.userservice.security.voter;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ClassUtils.isAssignable;

import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.security.model.RestPrincipal;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import java.util.Collection;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ConnectionBasedVoter implements AccessDecisionVoter<FilterInvocation> {

    private static final GrantedAuthority GRANT_MANAGER =
        new SimpleGrantedAuthority(RoleType.ROLE_MANAGER.getRoleName());

    private final RequestMatcher requiresAuthorizationRequestMatcher;
    private final Function<String, Id<User, Long>> idExtractor;
    private final RoleHierarchy roleHierarchy;

    public ConnectionBasedVoter(
        RequestMatcher requiresAuthorizationRequestMatcher,
        Function<String, Id<User, Long>> idExtractor,
        RoleHierarchy roleHierarchy) {

        checkNotNull(requiresAuthorizationRequestMatcher,
            "requiresAuthorizationRequestMatcher must be provided.");
        checkNotNull(idExtractor, "idExtractor must be provided.");

        this.requiresAuthorizationRequestMatcher = requiresAuthorizationRequestMatcher;
        this.idExtractor = idExtractor;
        this.roleHierarchy = roleHierarchy;
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

        // 본인 자신 또는 MANAGER 권한인 경우
        if (availableAccess(authentication, obtainTargetId(request))) {
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

    private Id<User, Long> obtainTargetId(HttpServletRequest request) {
        return idExtractor.apply(request.getRequestURI());
    }

    /**
     * 권한 확인하는 메서드
     *
     * @param authentication authentication
     * @param id             User id
     * @return boolean
     */
    private boolean availableAccess(Authentication authentication, Id<User, Long> id) {

        RestPrincipal principal = (RestPrincipal) authentication.getPrincipal();

        return principal.getId().equals(id.value())
            || roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())
            .contains(GRANT_MANAGER);
    }

}
