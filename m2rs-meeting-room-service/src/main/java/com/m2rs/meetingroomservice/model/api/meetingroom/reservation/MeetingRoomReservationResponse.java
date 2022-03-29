package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MeetingRoomReservationResponse {

    private final Long id;
    private final Long userId;
    private final Long mrId;
    private final String title;
    private final String description;
    private final Integer numberOfUsers;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private MeetingRoomReservationResponse(Long id, Long userId, Long mrId,
        String title, String description, Integer numberOfUsers, LocalDateTime startDate,
        LocalDateTime endDate,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.userId = userId;
        this.mrId = mrId;
        this.title = title;
        this.description = description;
        this.numberOfUsers = numberOfUsers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
