package com.m2rs.meetingroomservice.controller.meetingroom.reservation;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.SearchMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.SearchMeetingRoomReservationQueryCondition;
import com.m2rs.meetingroomservice.service.meetingroom.reservation.MeetingRoomReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("meeting/room/{mrId}/reservation")
public class MeetingRoomReservationController {

    private final MeetingRoomReservationService meetingRoomReservationService;

    @PostMapping(path = "")
    public ApiResult<MeetingRoomReservationResponse> createReservation(@PathVariable Long mrId,
        @RequestBody CreateMeetingRoomReservationRequest reservationRequest) {
        return ok(meetingRoomReservationService.createReservation(Id.of(MeetingRoom.class, mrId),
            reservationRequest));
    }

    @PutMapping(path = "{mrrId}")
    public ApiResult<MeetingRoomReservationResponse> modify(@PathVariable Long mrId,
        @PathVariable Long mrrId, @RequestBody ModifyMeetingRoomReservationRequest modifyRequest) {
        return ok(meetingRoomReservationService.modifyReservation(Id.of(MeetingRoom.class, mrId),
            Id.of(MeetingRoomReservation.class, mrrId),
            modifyRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{mrrId}")
    public ApiResult<MeetingRoomReservationResponse> remove(@PathVariable Long mrrId) {
        return ok(
            meetingRoomReservationService.removeReservation(Id.of(MeetingRoomReservation.class,
                mrrId)));
    }

    @GetMapping(path = "list")
    public ApiResult<List<MeetingRoomReservationResponse>> getReservationList(
        @PathVariable Long mrId,
        SearchMeetingRoomReservationRequest searchRequest) {
        return ok(meetingRoomReservationService.searchReservation(Id.of(MeetingRoom.class, mrId),
            searchRequest));
    }

}
