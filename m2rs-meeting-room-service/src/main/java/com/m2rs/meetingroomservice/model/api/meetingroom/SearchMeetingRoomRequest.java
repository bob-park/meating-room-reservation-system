package com.m2rs.meetingroomservice.model.api.meetingroom;

import com.m2rs.meetingroomservice.repository.meetingroom.query.SearchMeetingRoomQueryCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchMeetingRoomRequest {

    private Long comId;
    private Boolean isActive;
    private String name;

    @Builder
    private SearchMeetingRoomRequest(Long comId, Boolean isActive, String name) {
        this.comId = comId;
        this.isActive = isActive;
        this.name = name;
    }

    public SearchMeetingRoomQueryCondition getQueryCondition(){
        return SearchMeetingRoomQueryCondition.builder()
            .comId(comId)
            .isActive(isActive)
            .name(name)
            .build();
    }

}
