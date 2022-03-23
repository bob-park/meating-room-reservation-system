package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ModifyMeetingRoomReservationRequest {

    private Long mrId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
