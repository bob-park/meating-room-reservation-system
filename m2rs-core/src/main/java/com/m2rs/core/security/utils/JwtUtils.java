package com.m2rs.core.security.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.security.model.JwtClaimInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class JwtUtils {

    private JwtUtils() {

    }

    public static String newToken(Claims claims, JwtClaimInfo info) {

        checkArgument(isNotEmpty(claims) || claims.isEmpty(), "claims must be provided.");
        checkArgument(isNotEmpty(info), "info must be provided.");

        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, info.getClientSecret())
            .setIssuer(info.getIssuer())
            .setExpiration(toDate(info.getExpired()))
            .compact();
    }

    public static String refreshToken(String token, JwtClaimInfo info) {
        Claims claims = verify(token, info);

        claims.setExpiration(toDate(info.getExpired()));

        return newToken(claims, info);
    }

    public static Claims verify(String token, JwtClaimInfo info) {
        return Jwts.parser()
            .setSigningKey(info.getClientSecret())
            .parseClaimsJws(token)
            .getBody();
    }

    public static boolean isJwtValid(String jwt, JwtClaimInfo info){
        boolean returnValue = true;

        String subject = null;

        try {
            subject =
                Jwts.parser()
                    .setSigningKey(info.getClientSecret())
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    private static Date toDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }


}
