package com.m2rs.meetingroomservice.configure;

import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@EnableJpaAuditing
@EnableAspectJAutoProxy
@Configuration
public class AppConfiguration {

    public static final String BEARER_PREFIX = "Bearer";

    private final JwtClaimInfo jwtClaimInfo;

    @Bean
    public AuditorAware<Long> auditorUserProvider() {
        return () -> {

            HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            String header = request.getHeader(HttpHeaders.AUTHORIZATION);

            String token = header.replace(BEARER_PREFIX, "");

            JwtUtils.isJwtValid(token, jwtClaimInfo);

            Claims claims = JwtUtils.verify(token, jwtClaimInfo);

            Long userId = claims.get("id", Long.class);

            return Optional.ofNullable(userId);

        };
    }

}
