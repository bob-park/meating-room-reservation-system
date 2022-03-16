package com.m2rs.meetingroomservice.repository.meetingroom.query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MeetingRoomSearchCondition {

    private final Long comId;
    private final Boolean isActive;

    private final String name;

    @Builder
    private MeetingRoomSearchCondition(Long comId, Boolean isActive, String name) {
        this.comId = comId;
        this.isActive = isActive;
        this.name = name;
    }

}
