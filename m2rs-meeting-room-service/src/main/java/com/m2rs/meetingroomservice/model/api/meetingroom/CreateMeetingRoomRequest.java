package com.m2rs.meetingroomservice.model.api.meetingroom;

import lombok.Getter;

@Getter
public class CreateMeetingRoomRequest {

    private final Long comId;
    private final String name;

    public CreateMeetingRoomRequest(Long comId, String name) {
        this.comId = comId;
        this.name = name;
    }
}
