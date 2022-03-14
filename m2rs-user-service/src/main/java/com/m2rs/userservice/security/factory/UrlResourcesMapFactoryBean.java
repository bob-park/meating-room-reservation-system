package com.m2rs.userservice.security.factory;

import com.m2rs.userservice.service.resource.SecurityResourceService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RequiredArgsConstructor
public class UrlResourcesMapFactoryBean implements
    FactoryBean<Map<RequestMatcher, List<ConfigAttribute>>> {

    private final SecurityResourceService securityResourceService;
    private Map<RequestMatcher, List<ConfigAttribute>> resourceMap;

    @Override
    public Map<RequestMatcher, List<ConfigAttribute>> getObject() {

        if (resourceMap == null) {
            // 데이터가 없는 경우 DB 로 부터 가져오도록 설정
            init();
        }

        return resourceMap;
    }

    @Override
    public Class<?> getObjectType() {
        return Map.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private void init() {
        resourceMap = securityResourceService.getResourceUrlList();
    }

}
