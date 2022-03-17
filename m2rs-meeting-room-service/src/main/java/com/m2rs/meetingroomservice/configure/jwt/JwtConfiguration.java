package com.m2rs.meetingroomservice.configure.jwt;

import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.meetingroomservice.configure.jwt.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@ConfigurationPropertiesScan("com.m2rs.meetingroomservice.configure.jwt.properties")
public class JwtConfiguration {

    private final JwtProperties properties;

    @Bean
    public JwtClaimInfo getJwtClaimInfo() {
        return new JwtClaimInfo(properties.getClientSecret(),
            properties.getIssuer(),
            properties.getExpired());
    }

}
