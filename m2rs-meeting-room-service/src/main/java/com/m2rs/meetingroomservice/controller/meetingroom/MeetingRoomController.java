package com.m2rs.meetingroomservice.controller.meetingroom;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.ModifyMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.SearchMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.service.meetingroom.MeetingRoomService;
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
@RequestMapping("meeting/room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public ApiResult<MeetingRoomResponse> createMeetingRoom(
        @RequestBody CreateMeetingRoomRequest createRequest) {
        return ok(meetingRoomService.create(createRequest));
    }

    @GetMapping(path = "{meetingRoomId}")
    public ApiResult<MeetingRoomResponse> find(@PathVariable Long meetingRoomId) {
        return ok(meetingRoomService.find(Id.of(MeetingRoom.class, meetingRoomId)));
    }

    @PutMapping(path = "{meetingRoomId}")
    public ApiResult<MeetingRoomResponse> modify(@PathVariable Long meetingRoomId,
        @RequestBody ModifyMeetingRoomRequest modifyRequest) {
        return ok(meetingRoomService.modify(Id.of(MeetingRoom.class, meetingRoomId),
            modifyRequest));
    }

    @GetMapping(path = "list")
    public ApiResult<List<MeetingRoomResponse>> getList(SearchMeetingRoomRequest searchRequest) {
        return ok(meetingRoomService.search(searchRequest));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "{meetingRoomId}/inactive")
    public ApiResult<MeetingRoomResponse> inactive(@PathVariable Long meetingRoomId) {
        return ok(meetingRoomService.changeActive(Id.of(MeetingRoom.class, meetingRoomId), false));
    }

    @PutMapping(path = "{meetingRoomId}/active")
    public ApiResult<MeetingRoomResponse> active(@PathVariable Long meetingRoomId) {
        return ok(meetingRoomService.changeActive(Id.of(MeetingRoom.class, meetingRoomId), true));
    }
}
