package com.m2rs.core.commons.model.api.response;

import lombok.Getter;

@Getter
public class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }

    public Error(Throwable throwable) {
        this.message = throwable.getMessage();
    }
}
