package com.m2rs.meetingroomservice.configure.jwt.properties;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private static final Duration DEFAULT_EXPIRED = Duration.ofHours(1);

    private final Duration expired;
    private final String clientSecret;
    private final String issuer;

    public JwtProperties(Duration expired, String clientSecret, String issuer) {
        this.expired = defaultIfNull(expired, DEFAULT_EXPIRED);
        this.clientSecret = clientSecret;
        this.issuer = issuer;
    }

    public Duration getExpired() {
        return expired;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getIssuer() {
        return issuer;
    }

}
