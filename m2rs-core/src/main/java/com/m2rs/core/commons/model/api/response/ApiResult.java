package com.m2rs.core.commons.model.api.response;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final T result;
    private final Pagination page;
    private final boolean success;
    private final Error error;

    public ApiResult(T result, Pagination page,
        boolean success, Error error) {
        this.result = result;
        this.page = page;
        this.success = success;
        this.error = error;
    }

    public static <T> ApiResult<T> ok(T result) {
        return ok(result, null);
    }

    public static <T> ApiResult<T> ok(T result, Pagination page) {
        return new ApiResult<>(result, page, true, null);
    }

    public static <T> ApiResult<T> error(Throwable throwable) {
        return new ApiResult<>(null, null, false, new Error(throwable));
    }

}
