package com.m2rs.core.commons.exception.data;

import com.m2rs.core.commons.exception.DataException;
import com.m2rs.core.commons.exception.ServiceRuntimeException;

public class DataReferenceException extends DataException {

    public DataReferenceException(Class<?> reference, Class<?> target) {
        super(String.format("'%s' exist that uses '%s'.",
            target.getSimpleName(),
            reference.getSimpleName()));
    }

    public DataReferenceException(String reference, String target) {
        super(String.format("'%s' exist that uses '%s'.", target, reference));
    }
}
