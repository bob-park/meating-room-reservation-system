package com.m2rs.meetingroomservice.model.api.meetingroom;

import lombok.Getter;

@Getter
public class ModifyMeetingRoomRequest {

    private String name;
    private Boolean isActive;

}
