package com.m2rs.userservice.service.resource;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public interface SecurityResourceService {

    Map<RequestMatcher, List<ConfigAttribute>> getResourceUrlList();

}
