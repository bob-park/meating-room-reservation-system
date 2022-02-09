package com.m2rs.core.commons.model.api.response;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final boolean success;
    private final T result;
    private final Error error;

    public ApiResult(boolean success, T result, Error error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public static <T> ApiResult<T> OK(T result) {
        return new ApiResult<>(true, result, null);
    }

    public static <T> ApiResult<T> ERROR(Throwable throwable) {
        return new ApiResult<>(false, null, new Error(throwable));
    }

}
