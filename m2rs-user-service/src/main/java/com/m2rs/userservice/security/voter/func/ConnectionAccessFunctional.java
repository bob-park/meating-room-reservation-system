package com.m2rs.userservice.security.voter.func;

import org.springframework.security.core.Authentication;

public interface ConnectionAccessFunctional {

    boolean access(String requestUri, Authentication authentication);

}
