package com.m2rs.meetingroomservice.authentication.context;

import com.m2rs.core.security.model.RestPrincipal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAuthenticationContextHolder {

    private final ThreadLocal<RestPrincipal> principalHolder = new ThreadLocal<>();

    private static RestAuthenticationContextHolder instance;

    public static RestAuthenticationContextHolder getContextHolder() {

        if (instance == null) {
            instance = new RestAuthenticationContextHolder();
        }

        return instance;
    }

    public void setRestPrincipal(RestPrincipal principal) {

        RestPrincipal restPrincipal = this.principalHolder.get();

        if (restPrincipal == null) {

            log.debug("apply principal={}", principal);

            principalHolder.set(principal);
        }
    }

    public RestPrincipal getPrincipal() {
        return principalHolder.get();
    }

    public void remove() {

        RestPrincipal principal = getPrincipal();

        if (principal != null) {
            log.debug("remove principal={}", principal);
            principalHolder.remove();
        }

    }


}
