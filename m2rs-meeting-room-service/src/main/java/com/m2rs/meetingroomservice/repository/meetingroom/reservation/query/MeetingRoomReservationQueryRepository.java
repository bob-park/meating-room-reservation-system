package com.m2rs.meetingroomservice.repository.meetingroom.reservation.query;

import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomReservationQueryRepository {

    List<MeetingRoomReservation> search(SearchMeetingRoomReservationQueryCondition condition);

    boolean checkAvailableReservation(Id<MeetingRoom, Long> mrId,
        Id<MeetingRoomReservation, Long> excludeMrrId,
        LocalDateTime startDate,
        LocalDateTime endDate);

}
