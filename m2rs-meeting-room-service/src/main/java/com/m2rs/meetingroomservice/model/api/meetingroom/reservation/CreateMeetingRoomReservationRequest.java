package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateMeetingRoomReservationRequest {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Builder
    private CreateMeetingRoomReservationRequest(LocalDateTime startDate,
        LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
