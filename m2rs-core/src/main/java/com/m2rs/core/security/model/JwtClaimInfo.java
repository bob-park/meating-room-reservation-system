package com.m2rs.core.security.model;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class JwtClaimInfo {

    private static final long DEFAULT_EXPIRED_MINUTES = 30;

    private final String clientSecret;
    private final String issuer;
    private final Duration expirationTime;

    public JwtClaimInfo(String clientSecret, String issuer, Duration expirationTime) {

        checkArgument(isNotEmpty(clientSecret), "clientSecret must be provided.");
        checkArgument(isNotEmpty(issuer), "issuer must be provided.");

        this.clientSecret = clientSecret;
        this.issuer = issuer;
        this.expirationTime =
            defaultIfNull(expirationTime, Duration.ofMinutes(DEFAULT_EXPIRED_MINUTES));
    }

    public LocalDateTime getExpired() {
        return LocalDateTime.now().plus(getExpirationTime());
    }
}
