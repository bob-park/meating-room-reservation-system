package com.m2rs.meetingroomservice.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.core.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        // Java time module
        JavaTimeModule jtm = new JavaTimeModule();
        jtm.addDeserializer(
            LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        Jackson2ObjectMapperBuilder builder =
            new Jackson2ObjectMapperBuilder() {
                @Override
                public void configure(ObjectMapper objectMapper) {
                    super.configure(objectMapper);
                    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
                    objectMapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
                    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                }
            };
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.modulesToInstall(jtm);



        return builder;
    }

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
