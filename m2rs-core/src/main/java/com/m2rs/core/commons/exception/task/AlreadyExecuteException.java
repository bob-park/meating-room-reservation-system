package com.m2rs.core.commons.exception.task;

import com.m2rs.core.commons.exception.ServiceRuntimeException;

public class AlreadyExecuteException extends ServiceRuntimeException {

    public AlreadyExecuteException() {
        super("Already executed.");
    }
}
