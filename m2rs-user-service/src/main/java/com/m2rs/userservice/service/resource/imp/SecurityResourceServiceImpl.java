package com.m2rs.userservice.service.resource.imp;

import com.google.common.collect.Maps;
import com.m2rs.userservice.repository.resource.ResourceRepository;
import com.m2rs.userservice.service.resource.SecurityResourceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SecurityResourceServiceImpl implements SecurityResourceService {

    private final ResourceRepository resourceRepository;


    @Override
    public Map<RequestMatcher, List<ConfigAttribute>> getResourceUrlList() {

        Map<RequestMatcher, List<ConfigAttribute>> result = Maps.newLinkedHashMap();

        resourceRepository.findAllUrlResources()
            .forEach(resource -> {
                List<ConfigAttribute> configAttributeList = new ArrayList<>();

                resource.getRoleSet()
                    .forEach(role ->
                        configAttributeList.add(
                            new SecurityConfig(role.getRolesName().getRoleName())));

                result.put(new AntPathRequestMatcher(resource.getResourceName()),
                    configAttributeList);
            });

        return result;
    }
}
