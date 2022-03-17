package com.m2rs.userservice.configure.security;


import static org.apache.commons.lang3.math.NumberUtils.toLong;

import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.JwtClaimInfo;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.security.entrypoint.RestLoginAuthenticationEntryPoint;
import com.m2rs.userservice.security.factory.UrlResourcesMapFactoryBean;
import com.m2rs.userservice.security.filter.PermitAllFilter;
import com.m2rs.userservice.security.filter.RestAuthenticationFilter;
import com.m2rs.userservice.security.filter.RestLoginProcessingFilter;
import com.m2rs.userservice.security.handler.RestAccessDeniedHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationFailureHandler;
import com.m2rs.userservice.security.handler.RestAuthenticationSuccessHandler;
import com.m2rs.userservice.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.m2rs.userservice.security.provider.RestAuthenticationProvider;
import com.m2rs.userservice.security.voter.ConnectionBasedVoter;
import com.m2rs.userservice.service.resource.SecurityResourceService;
import com.m2rs.userservice.service.role.RoleHierarchyService;
import com.m2rs.userservice.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityResourceService securityResourceService;


    private final RoleHierarchyService roleHierarchyService;

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
            .anyRequest()
            .authenticated();

        http
            .addFilterBefore(getSecurityAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);

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

    @Bean
    public PermitAllFilter customFilterSecurityInterceptor() throws Exception {

        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);

        permitAllFilter.setSecurityMetadataSource(
            urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(unanimousBased());
        permitAllFilter.setAuthenticationManager(authenticationManagerBean());

        return permitAllFilter;
    }

    /*
     metadata source
     */
    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource(
            urlResourcesMapFactoryBean().getObject(), securityResourceService);
    }

    @Bean
    public UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        return new UrlResourcesMapFactoryBean(securityResourceService);
    }

    /*
     Access Decision Manager
     */
    @Bean
    public AccessDecisionManager unanimousBased() {

        List<AccessDecisionVoter<?>> accessDecisionVoters = new ArrayList<>();

        accessDecisionVoters.add(getConnectionBasedVoter());
        accessDecisionVoters.add(getRoleVoter());

        return new UnanimousBased(accessDecisionVoters);
    }


    /*
     voter
     */
    @Bean
    public AccessDecisionVoter<?> getRoleVoter() {
        return new RoleHierarchyVoter(getRoleHierarchy());
    }

    @Bean
    public ConnectionBasedVoter getConnectionBasedVoter() {

        Pattern pattern = Pattern.compile("^/user/(\\d+)");

        RegexRequestMatcher regexRequestMatcher = new RegexRequestMatcher(pattern.pattern(), null);

        return new ConnectionBasedVoter(regexRequestMatcher, url -> {
            Matcher matcher = pattern.matcher(url);

            long userId = matcher.find() ? toLong(matcher.group(1), -1) : -1;

            return Id.of(User.class, userId);
        });
    }

    /*
     role hierarchy
     */
    @Bean
    public RoleHierarchyImpl getRoleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        Map<String, List<String>> roleHierarchyMap = roleHierarchyService.getRoleHierarchyMap();

        String roles = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);

        log.debug("roleHierarchy={}", roles);

        roleHierarchy.setHierarchy(roles);

        return roleHierarchy;
    }
}
