package com.m2rs.core.commons.exception;

public class DataReferenceException extends ServiceRuntimeException {

    public DataReferenceException(Class<?> reference, Class<?> target) {
        super(String.format("'%s' exist that uses '%s'.",
            target.getSimpleName(),
            reference.getSimpleName()));
    }

    public DataReferenceException(String reference, String target) {
        super(String.format("'%s' exist that uses '%s'.", target, reference));
    }
}
