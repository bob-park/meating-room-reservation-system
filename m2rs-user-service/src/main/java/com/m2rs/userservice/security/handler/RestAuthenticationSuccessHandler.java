package com.m2rs.userservice.security.handler;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.userservice.security.model.RestAuthenticationResult;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        RestAuthenticationToken authenticationToken = (RestAuthenticationToken) authentication;

        RestAuthenticationResult details = (RestAuthenticationResult) authenticationToken.getDetails();

        response.setStatus(HttpStatus.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), ok(details));

    }
}
