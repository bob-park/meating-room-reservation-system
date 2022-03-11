package com.m2rs.userservice.configure.security;


import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.userservice.security.entrypoint.RestLoginAuthenticationEntryPoint;
import com.m2rs.userservice.security.filter.RestLoginProcessingFilter;
import com.m2rs.userservice.security.handler.RestAccessDeniedHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationFailureHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationSuccessHandler;
import com.m2rs.userservice.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtClaimInfo jwtClaimInfo;

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(RestLoginProcessingFilter.REST_LOGIN_URI).permitAll()
            .anyRequest()
            .authenticated();

        http.exceptionHandling()
            .authenticationEntryPoint(new RestLoginAuthenticationEntryPoint())
            .accessDeniedHandler(getAccessDeniedHandler());

        http.formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .cors().disable();
    }

    @Bean
    public RestLoginProcessingFilter getRestLoginProcessingFilter() throws Exception {
        RestLoginProcessingFilter restLoginProcessingFilter = new RestLoginProcessingFilter();

        restLoginProcessingFilter
            .setAuthenticationManager(authenticationManagerBean());
        restLoginProcessingFilter
            .setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        restLoginProcessingFilter
            .setAuthenticationFailureHandler(getAuthenticationFailureHandler());

        return restLoginProcessingFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new RestAuthenticationSuccessHandler(jwtClaimInfo);
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }
}
