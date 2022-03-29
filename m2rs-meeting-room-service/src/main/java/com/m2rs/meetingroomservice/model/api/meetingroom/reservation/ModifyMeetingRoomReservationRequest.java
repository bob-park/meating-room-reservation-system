package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyMeetingRoomReservationRequest {

    private String title;
    private String description;
    private Integer numberOfUsers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    private ModifyMeetingRoomReservationRequest(String title, String description,
        Integer numberOfUsers, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.numberOfUsers = numberOfUsers;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
