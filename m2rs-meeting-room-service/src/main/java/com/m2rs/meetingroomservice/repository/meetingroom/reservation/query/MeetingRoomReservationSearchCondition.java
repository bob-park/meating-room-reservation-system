package com.m2rs.meetingroomservice.repository.meetingroom.reservation.query;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
public class MeetingRoomReservationSearchCondition {

    @DateTimeFormat(iso = ISO.DATE)
    private final LocalDate startDateFrom;

    @DateTimeFormat(iso = ISO.DATE)
    private final LocalDate startDateTo;

    private final Long mrId;
    private final Long userId;


    @Builder
    private MeetingRoomReservationSearchCondition(LocalDate startDateFrom,
        LocalDate startDateTo, Long mrId, Long userId) {
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.mrId = mrId;
        this.userId = userId;
    }
}
