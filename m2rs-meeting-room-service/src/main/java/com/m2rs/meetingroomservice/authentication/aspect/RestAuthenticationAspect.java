package com.m2rs.meetingroomservice.authentication.aspect;

import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.model.RestPrincipal;
import com.m2rs.core.security.utils.JwtUtils;
import com.m2rs.meetingroomservice.authentication.context.RestAuthenticationContextHolder;
import com.m2rs.meetingroomservice.configure.AppConfiguration;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class RestAuthenticationAspect {

    private final JwtClaimInfo jwtClaimInfo;

    @Around(value = "execution(* com.m2rs.meetingroomservice.controller..*Controller.*(..))")
    public Object setControllerPrincipal(ProceedingJoinPoint joinPoint)
        throws Throwable {

        Object returnValue = null;

        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token = header.replace(AppConfiguration.BEARER_PREFIX, "");

        boolean isExistToken = StringUtils.isNotBlank(token);

        if (!isExistToken) {
            log.debug("no authentication token...");

            return joinPoint.proceed();
        }

        JwtUtils.isJwtValid(token, jwtClaimInfo);

        Claims claims = JwtUtils.verify(token, jwtClaimInfo);

        RestPrincipal principal = RestPrincipal.builder()
            .id(claims.get("id", Long.class))
            .comId(claims.get("comId", Long.class))
            .departmentId(claims.get("departmentId", Long.class))
            .email(claims.get("email", String.class))
            .name(claims.get("name", String.class))
            .build();

        RestAuthenticationContextHolder.getContextHolder().setRestPrincipal(principal);

        returnValue = joinPoint.proceed();

        RestAuthenticationContextHolder.getContextHolder().remove();

        return returnValue;

    }

}
