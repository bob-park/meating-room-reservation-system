package com.m2rs.meetingroomservice.exception;

import com.m2rs.core.commons.exception.ServiceRuntimeException;

public class ReservationUserDifferentException extends ServiceRuntimeException {

    public ReservationUserDifferentException() {
        super("Reservation user is different .");
    }
}
