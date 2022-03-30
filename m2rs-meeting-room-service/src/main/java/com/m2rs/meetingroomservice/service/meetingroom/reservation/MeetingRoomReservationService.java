package com.m2rs.meetingroomservice.service.meetingroom.reservation;

import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.SearchMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import java.util.List;

public interface MeetingRoomReservationService {

    MeetingRoomReservationResponse createReservation(Id<MeetingRoom, Long> mrId,
        CreateMeetingRoomReservationRequest reservationRequest);

    List<MeetingRoomReservationResponse> searchReservation(Id<MeetingRoom, Long> mrId,
        SearchMeetingRoomReservationRequest searchRequest);

    MeetingRoomReservationResponse modifyReservation(Id<MeetingRoom, Long> mrId,
        Id<MeetingRoomReservation, Long> mrrId,
        ModifyMeetingRoomReservationRequest modifyRequest);

    MeetingRoomReservationResponse removeReservation(Id<MeetingRoomReservation, Long> mrrId);

}
