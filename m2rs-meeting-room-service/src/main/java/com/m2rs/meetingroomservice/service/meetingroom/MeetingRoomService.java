package com.m2rs.meetingroomservice.service.meetingroom;

import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;

public interface MeetingRoomService {

    MeetingRoomResponse create(CreateMeetingRoomRequest createRequest);

}
