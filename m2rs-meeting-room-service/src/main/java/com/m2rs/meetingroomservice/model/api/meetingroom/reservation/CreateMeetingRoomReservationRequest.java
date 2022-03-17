package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateMeetingRoomReservationRequest {

    private final Long mrId;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    @Builder
    private CreateMeetingRoomReservationRequest(Long mrId, LocalDateTime startDate,
        LocalDateTime endDate) {
        this.mrId = mrId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
