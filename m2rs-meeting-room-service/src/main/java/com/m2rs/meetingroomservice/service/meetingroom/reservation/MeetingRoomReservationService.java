package com.m2rs.meetingroomservice.service.meetingroom.reservation;

import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationSearchCondition;
import java.util.List;

public interface MeetingRoomReservationService {

    MeetingRoomReservationResponse createReservation(CreateMeetingRoomReservationRequest reservationRequest);

    List<MeetingRoomReservationResponse> searchReservation(MeetingRoomReservationSearchCondition condition);

}
