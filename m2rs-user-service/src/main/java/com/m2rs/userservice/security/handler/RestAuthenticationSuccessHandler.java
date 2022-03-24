package com.m2rs.userservice.security.handler;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.userservice.security.model.RestAuthenticationResult;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        RestAuthenticationToken authenticationToken = (RestAuthenticationToken) authentication;

        RestAuthenticationResult details = (RestAuthenticationResult) authenticationToken.getDetails();

        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        mapper.writeValue(response.getWriter(), ok(details));

    }
}
