package com.m2rs.meetingroomservice.model.api.meetingroom;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMeetingRoomRequest {

    private Long comId;

    private String name;

    @Builder
    private CreateMeetingRoomRequest(Long comId, String name) {
        this.comId = comId;
        this.name = name;
    }
}
