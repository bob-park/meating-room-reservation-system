package com.m2rs.meetingroomservice.model.api.meetingroom;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MeetingRoomResponse {

    private final Long id;
    private final Long comId;
    private final String name;
    private final Boolean isActive;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private MeetingRoomResponse(Long id, Long comId, String name, Boolean isActive,
        LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.comId = comId;
        this.name = name;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
