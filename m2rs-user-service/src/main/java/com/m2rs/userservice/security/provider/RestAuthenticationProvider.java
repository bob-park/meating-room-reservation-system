package com.m2rs.userservice.security.provider;

import com.m2rs.core.security.model.JwtClaim;
import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.utils.JwtUtils;
import com.m2rs.userservice.model.api.user.UserLoginRequest;
import com.m2rs.userservice.model.api.user.UserResponse;
import com.m2rs.userservice.security.model.RestAuthenticationResult;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import com.m2rs.userservice.service.user.UserAuthenticationService;
import com.m2rs.userservice.service.user.UserService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthenticationService authenticationService;
    private final JwtClaimInfo jwtClaimInfo;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        RestAuthenticationToken authenticationToken = (RestAuthenticationToken) authentication;

        return processUserAuthentication(authenticationToken.getRequest());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
    }

    private Authentication processUserAuthentication(UserLoginRequest request) {
        try {
            UserResponse userResponse = authenticationService.login(request.getEmail(),
                request.getPassword());

            RestAuthenticationToken authentication =
                new RestAuthenticationToken(userResponse.getEmail(),
                    null,
                    userResponse.getRoleTypes().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList())
                );

            String token = JwtUtils.newToken(
                JwtClaim.builder()
                    .id(userResponse.getId())
                    .comId(userResponse.getComId())
                    .departmentId(userResponse.getDepartmentId())
                    .email(userResponse.getEmail())
                    .name(userResponse.getName())
                    .roleType(userResponse.getRoleTypes().stream().findFirst().orElse(null))
                    .build().toClaims(),
                jwtClaimInfo
            );

            authentication.setDetails(new RestAuthenticationResult(token, userResponse));

            return authentication;
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }
}
