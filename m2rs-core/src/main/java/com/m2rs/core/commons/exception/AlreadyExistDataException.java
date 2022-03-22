package com.m2rs.core.commons.exception;

public class AlreadyExistDataException extends ServiceRuntimeException {

    public AlreadyExistDataException(Class<?> target, String data) {
        super(String.format("'%s' already exist in the '%s'.", data, target.getSimpleName()));
    }

    public AlreadyExistDataException(String target, String data) {
        super(String.format("'%s' already exist in the '%s'.", data, target));
    }
}
