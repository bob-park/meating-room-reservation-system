package com.m2rs.meetingroomservice.exception;

import com.m2rs.core.commons.exception.ServiceRuntimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AlreadyReservationException extends ServiceRuntimeException {

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AlreadyReservationException(LocalDateTime startDate, LocalDateTime endDate) {
        super(String.format("already exist reservation. (%s ~ %s)",
            startDate.format(DEFAULT_DATE_TIME_FORMAT),
            endDate.format(DEFAULT_DATE_TIME_FORMAT)));
    }
}
