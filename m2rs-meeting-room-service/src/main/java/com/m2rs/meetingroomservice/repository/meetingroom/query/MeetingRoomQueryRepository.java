package com.m2rs.meetingroomservice.repository.meetingroom.query;

import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import java.util.List;

public interface MeetingRoomQueryRepository {

    List<MeetingRoom> search(SearchMeetingRoomQueryCondition condition);

}
