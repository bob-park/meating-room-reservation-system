package com.m2rs.userservice.service.resource.impl;

import com.m2rs.userservice.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.m2rs.userservice.service.resource.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService {

    private final  UrlFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public Boolean reloadResource() {

        securityMetadataSource.reload();

        log.info("reload security resource metadata source.");

        return true;
    }
}
