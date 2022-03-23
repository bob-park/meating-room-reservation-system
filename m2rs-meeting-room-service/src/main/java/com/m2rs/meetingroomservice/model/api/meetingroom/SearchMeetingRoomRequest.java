package com.m2rs.meetingroomservice.model.api.meetingroom;

import com.m2rs.meetingroomservice.repository.meetingroom.query.SearchMeetingRoomQueryCondition;
import lombok.Getter;

@Getter
public class SearchMeetingRoomRequest {

    private Long comId;
    private Boolean isActive;
    private String name;

    public SearchMeetingRoomQueryCondition getQueryCondition(){
        return SearchMeetingRoomQueryCondition.builder()
            .comId(comId)
            .isActive(isActive)
            .name(name)
            .build();
    }

}
