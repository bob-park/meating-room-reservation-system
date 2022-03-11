package com.m2rs.userservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.userservice.model.api.user.UserLoginRequest;
import com.m2rs.userservice.security.model.CustomAuthenticationToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class RestLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    public RestLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        UserLoginRequest userLoginRequest =
            mapper.readValue(request.getReader(), UserLoginRequest.class);

        if (StringUtils.isEmpty(userLoginRequest.getEmail()) ||
            StringUtils.isEmpty(userLoginRequest.getPassword())) {
            throw new IllegalArgumentException("Email or Password is empty.");
        }

        CustomAuthenticationToken customAuthenticationToken =
            new CustomAuthenticationToken(userLoginRequest.getEmail(),
                userLoginRequest.getPassword());

        return getAuthenticationManager().authenticate(customAuthenticationToken);
    }
}