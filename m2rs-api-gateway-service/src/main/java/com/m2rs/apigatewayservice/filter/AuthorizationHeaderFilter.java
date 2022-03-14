package com.m2rs.apigatewayservice.filter;

import static com.m2rs.core.commons.model.api.response.ApiResult.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2rs.core.commons.exception.ServiceRuntimeException;
import com.m2rs.core.commons.model.api.response.ApiResult;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends
    AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final String JWT_CLIENT_SECRET = "jwt.client-secret";

    private final ObjectMapper mapper = new ObjectMapper();

    private final Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // header token 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header.", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT token is not valid.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {

        boolean returnValue = true;

        String subject = null;

        try {
            subject =
                Jwts.parser()
                    .setSigningKey(getTokenSecret())
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

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);

        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        log.error(message);

        ApiResult<Object> result = error(new ServiceRuntimeException(message));

        try {
            byte[] body = mapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8);

            DataBuffer buffer = response.bufferFactory().wrap(body);

            return response.writeWith(Mono.just(buffer));

        } catch (JsonProcessingException e) {
            log.warn("JSON parse error - {}", e.getMessage());
        }

        return response.setComplete();
    }

    private String getTokenSecret() {
        return env.getProperty(JWT_CLIENT_SECRET);
    }

    public static class Config {

    }


}
