package com.m2rs.userservice.configure.security;


import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.userservice.security.entrypoint.RestLoginAuthenticationEntryPoint;
import com.m2rs.userservice.security.filter.RestLoginProcessingFilter;
import com.m2rs.userservice.security.filter.RestAuthenticationFilter;
import com.m2rs.userservice.security.handler.RestAccessDeniedHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationFailureHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationSuccessHandler;
import com.m2rs.userservice.security.provider.RestAuthenticationProvider;
import com.m2rs.userservice.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtClaimInfo jwtClaimInfo;

    private final String[] permitAllResources = {};

    @Override
    public void configure(WebSecurity web) {
        // static resource 관련 uri 는 ignore 될 수 있도록 함
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider(null));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(RestLoginProcessingFilter.REST_LOGIN_URI, "/actuator/**").permitAll()
            .anyRequest()
            .authenticated()
            .accessDecisionManager(affirmativeBased());

        http.addFilterBefore(getSecurityAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);

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
    public RestAuthenticationProvider getAuthenticationProvider(UserService userService) {
        return new RestAuthenticationProvider(userService, jwtClaimInfo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new RestAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new RestAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    /*
      filter
     */
    @Bean
    public RestAuthenticationFilter getSecurityAuthenticationFilter() {
        return new RestAuthenticationFilter();
    }

    /*
     Voter
     */
    @Bean
    public AccessDecisionManager affirmativeBased() {

        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();

        accessDecisionVoters.add(new WebExpressionVoter());
        accessDecisionVoters.add(getRoleVoter());

        return new AffirmativeBased(accessDecisionVoters);
    }


    /*
     Role Hierarchy
     */
    @Bean
    public AccessDecisionVoter<?> getRoleVoter() {
        return new RoleHierarchyVoter(getRoleHierarchy());
    }

    @Bean
    public RoleHierarchyImpl getRoleHierarchy() {
        return new RoleHierarchyImpl();
    }
}
