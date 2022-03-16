package com.m2rs.meetingroomservice.service.meetingroom.reservation;

import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;

public interface MeetingRoomReservationService {

    MeetingRoomReservationResponse createReservation(CreateMeetingRoomReservationRequest reservationRequest);

}
