package com.m2rs.meetingroomservice.controller.meetingroom.reservation;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.service.meetingroom.reservation.MeetingRoomReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("meeting/room/reservation")
public class MeetingRoomReservationController {

    private final MeetingRoomReservationService meetingRoomReservationService;

    @PostMapping(path = "")
    public ApiResult<MeetingRoomReservationResponse> createReservation(
        @RequestBody CreateMeetingRoomReservationRequest reservationRequest) {
        return ok(meetingRoomReservationService.createReservation(reservationRequest));
    }

}
