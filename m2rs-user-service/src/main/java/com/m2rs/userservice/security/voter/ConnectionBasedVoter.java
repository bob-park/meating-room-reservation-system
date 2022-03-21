package com.m2rs.userservice.security.voter;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import com.m2rs.userservice.security.model.RestPrincipal;
import java.util.Collection;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
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

    private static final GrantedAuthority GRANT_ADMIN =
        new SimpleGrantedAuthority(RoleType.ROLE_ADMIN.getRoleName());

    private final RequestMatcher requiresAuthorizationRequestMatcher;
    private final Function<String, UserConnect> idExtractor;
    private final RoleHierarchy roleHierarchy;

    public ConnectionBasedVoter(
        RequestMatcher requiresAuthorizationRequestMatcher,
        Function<String, UserConnect> idExtractor,
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

    private UserConnect obtainTargetId(HttpServletRequest request) {
        return idExtractor.apply(request.getRequestURI());
    }

    /**
     * 권한 확인하는 메서드
     * <p>
     * - 사용자인 경우
     * <pre>
     *     - 같은 company && 같은 user
     * </pre>
     * <p>
     * - 매니저인 경우
     * <pre>
     *     - 같은 company && 모든 user
     * </pre>
     * <p>
     * - 관리자인 경우
     * <pre>
     *     - 모든 company && 모든 user
     * </pre>
     *
     * @param authentication authentication
     * @param userConnect    userConnect
     * @return boolean
     */
    private boolean availableAccess(Authentication authentication, UserConnect userConnect) {

        RestPrincipal principal = (RestPrincipal) authentication.getPrincipal();

        boolean isEqualCompany = isNotEmpty(principal.getComId())
            && principal.getComId().equals(userConnect.getComId().value());

        if (isEqualCompany) {

            // check user authority
            if (principal.getId().equals(userConnect.getUserId().value())) {
                return true;
            }

            boolean isNotNullDepartment = isNotEmpty(principal.getDepartmentId())
                && isNotEmpty(userConnect.getDepartmentId());

            if (isNotNullDepartment) {
                return principal.getDepartmentId().equals(userConnect.getDepartmentId().value());
            }

            // check manager authority
            return roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())
                .contains(GRANT_MANAGER);
        }

        // check admin authority
        return roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities())
            .contains(GRANT_ADMIN);
    }

    @Getter
    public static class UserConnect {

        private final Id<Company, Long> comId;
        private final Id<Department, Long> departmentId;
        private final Id<User, Long> userId;

        @Builder
        private UserConnect(
            Id<Company, Long> comId,
            Id<Department, Long> departmentId,
            Id<User, Long> userId) {
            this.comId = comId;
            this.departmentId = departmentId;
            this.userId = userId;
        }
    }

}
