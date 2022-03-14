package com.m2rs.userservice.exception;

import com.m2rs.core.commons.exception.ServiceRuntimeException;

public class UserEmailNotFound extends ServiceRuntimeException {

    public UserEmailNotFound(String email) {
        super(String.format("Not found user email. ('%s')", email));
    }
}
