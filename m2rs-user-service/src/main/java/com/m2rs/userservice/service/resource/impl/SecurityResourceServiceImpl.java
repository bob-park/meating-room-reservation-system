package com.m2rs.userservice.service.resource.impl;

import com.google.common.collect.Maps;
import com.m2rs.core.commons.exception.ServiceRuntimeException;
import com.m2rs.userservice.repository.resource.ResourceRepository;
import com.m2rs.userservice.service.resource.SecurityResourceService;
import com.m2rs.userservice.type.ResourceKind;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
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

                result.put(createRequestMatcher(resource.getResourceName(),
                        resource.getHttpMethod(),
                        resource.getResourceKind()
                    ),
                    configAttributeList);
            });

        return result;
    }

    private RequestMatcher createRequestMatcher(String path, String httpMethod, ResourceKind kind) {

        RequestMatcher matcher = null;

        switch (kind) {

            case PATH:
                matcher = new AntPathRequestMatcher(path, httpMethod);
                break;

            case REGEX:
                matcher = new RegexRequestMatcher(path, httpMethod);
                break;

            default:
                throw new ServiceRuntimeException(
                    String.format("not support resource kind. (%s)", kind));
        }

        return matcher;


    }
}
