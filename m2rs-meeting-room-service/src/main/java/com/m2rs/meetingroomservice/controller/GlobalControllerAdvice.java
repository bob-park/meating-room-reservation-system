package com.m2rs.meetingroomservice.controller;

import static com.m2rs.core.commons.model.api.response.ApiResult.error;

import com.m2rs.core.commons.exception.DataException;
import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.meetingroomservice.exception.ReservationUserDifferentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, DataException.class})
    public <T> ApiResult<T> badRequest(IllegalArgumentException e) {
        log.warn(e.getMessage());

        return error(e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ReservationUserDifferentException.class})
    public <T> ApiResult<T> forbidden(Exception e) {

        log.warn(e.getMessage());

        return error(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public <T> ApiResult<T> notFound(NotFoundException e) {
        log.warn(e.getMessage());

        return error(e);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public <T> ApiResult<T> internalServerError(Exception e) {
        log.error("Service Error - {}", e.getMessage(), e);

        return error(e);
    }

}
