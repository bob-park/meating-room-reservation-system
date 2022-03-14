package com.m2rs.userservice.security.filter;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.utils.JwtUtils;
import com.m2rs.userservice.security.model.RestAuthentication;
import com.m2rs.userservice.security.model.RestAuthenticationToken;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
public class RestAuthenticationFilter extends GenericFilterBean {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    private static final String AUTH_TOKEN_HEADER = HttpHeaders.AUTHORIZATION;

    private JwtClaimInfo jwtClaimInfo;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authorizationToken = obtainAuthorizationToken(request);
            if (authorizationToken != null) {
                try {
                    Claims claims = verify(authorizationToken);
                    log.debug("Jwt parse result: {}", claims);

                    // 만료 10분 전
                    if (canRefresh(claims, jwtClaimInfo.getExpirationTime().toMillis())) {
                        String refreshedToken =
                            JwtUtils.refreshToken(authorizationToken, jwtClaimInfo);
                        response.setHeader(AUTH_TOKEN_HEADER, refreshedToken);
                    }

                    Long id = claims.get("id", Long.class);
                    String email = claims.get("email", String.class);
                    String name = claims.get("name", String.class);

                    List<GrantedAuthority> authorities = obtainAuthorities(claims);

                    if (isNotEmpty(email) && !authorities.isEmpty()) {

                        RestAuthenticationToken authentication = new RestAuthenticationToken(
                            RestAuthentication.builder()
                                .id(id)
                                .email(email)
                                .name(name)
                                .build(),
                            null,
                            authorities);

                        authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    log.warn("Jwt processing failed: {}", e.getMessage());
                }
            }
        } else {
            log.debug(
                "SecurityContextHolder not populated with security token, as it already contained: '{}'",
                SecurityContextHolder.getContext().getAuthentication());
        }

        chain.doFilter(request, response);
    }

    private boolean canRefresh(Claims claims, long refreshRangeMillis) {
        long exp = claims.getExpiration().getTime();
        if (exp > 0) {
            long remain = exp - System.currentTimeMillis();
            return remain < refreshRangeMillis;
        }
        return false;
    }

    private List<GrantedAuthority> obtainAuthorities(Claims claims) {
        String role = claims.get("role", String.class);

        return isEmpty(role)
            ? Collections.emptyList()
            : Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    private String obtainAuthorizationToken(HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {

            if (log.isDebugEnabled()) {
                log.debug("Jwt authorization api detected: {}", token);
            }

            try {
                token = URLDecoder.decode(token, StandardCharsets.UTF_8);
                String[] parts = token.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];
                    return BEARER.matcher(scheme).matches() ? credentials : null;
                }
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
            }
        }

        return null;
    }

    private Claims verify(String token) {
        return JwtUtils.verify(token, jwtClaimInfo);
    }

    @Autowired
    public void setJwtClaimInfo(JwtClaimInfo jwtClaimInfo) {
        this.jwtClaimInfo = jwtClaimInfo;
    }

}
