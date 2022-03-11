package com.m2rs.userservice.security.handler;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.core.security.model.JwtClaim;
import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.utils.JwtUtils;
import com.m2rs.userservice.model.entity.User;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    private final JwtClaimInfo jwtClaimInfo;

    public RestAuthenticationSuccessHandler(JwtClaimInfo jwtClaimInfo) {
        this.jwtClaimInfo = jwtClaimInfo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal();

        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        JwtClaim jwtClaim = JwtClaim.builder()
            .id(user.getId())
            .departmentId(isNotEmpty(user.getDepartment()) ? user.getDepartment().getId() : null)
            .email(user.getEmail())
            .name(user.getName())
            .build();

        String token = JwtUtils.newToken(jwtClaim.toClaims(), jwtClaimInfo);

        mapper.writeValue(response.getWriter(), ok(Collections.singletonMap("token", token)));

    }
}
