package com.m2rs.meetingroomservice.repository.meetingroom.reservation.query;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchMeetingRoomReservationQueryCondition {

    private final LocalDate startDateFrom;
    private final LocalDate startDateTo;
    private final Long mrId;
    private final Long userId;

    @Builder
    private SearchMeetingRoomReservationQueryCondition(LocalDate startDateFrom,
        LocalDate startDateTo, Long mrId, Long userId) {
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.mrId = mrId;
        this.userId = userId;
    }
}
