package com.m2rs.core.commons.exception;

public class NotFoundException extends ServiceRuntimeException {

    public NotFoundException(Class<?> clazz, Object value) {
        this(clazz.getName(), value);
    }

    public NotFoundException(String targetName, Object value) {
        super(String.format("Could not found '%s' with query values (%s)", targetName, value));
    }
}
