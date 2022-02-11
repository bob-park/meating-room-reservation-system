package com.m2rs.core.commons.model.api.response;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final T result;
    private final boolean success;
    private final Error error;

    public ApiResult(T result, boolean success, Error error) {
        this.result = result;
        this.success = success;
        this.error = error;
    }

    public static <T> ApiResult<T> OK(T result) {
        return new ApiResult<>(result, true, null);
    }

    public static <T> ApiResult<T> ERROR(Throwable throwable) {
        return new ApiResult<>(null, false, new Error(throwable));
    }

}
