package com.m2rs.core.commons.exception.data;

import com.m2rs.core.commons.exception.DataException;
import com.m2rs.core.commons.exception.ServiceRuntimeException;

public class AlreadyExistDataException extends DataException {

    public AlreadyExistDataException(Class<?> target, String data) {
        super(String.format("'%s' already exist in the '%s'.", data, target.getSimpleName()));
    }

    public AlreadyExistDataException(String target, String data) {
        super(String.format("'%s' already exist in the '%s'.", data, target));
    }
}
