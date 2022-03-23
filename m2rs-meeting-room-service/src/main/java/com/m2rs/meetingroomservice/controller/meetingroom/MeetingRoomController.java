package com.m2rs.meetingroomservice.controller.meetingroom;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.ModifyMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomSearchCondition;
import com.m2rs.meetingroomservice.service.meetingroom.MeetingRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("meeting/room")
public class MeetingRoomController {

    private final MeetingRoomService meetingRoomService;

    @PostMapping(path = "")
    public ApiResult<MeetingRoomResponse> createMeetingRoom(
        @RequestBody CreateMeetingRoomRequest createRequest) {
        return ok(meetingRoomService.create(createRequest));
    }

    @GetMapping(path = "list")
    public ApiResult<List<MeetingRoomResponse>> getList(MeetingRoomSearchCondition condition) {
        return ok(meetingRoomService.search(condition));
    }

    @PutMapping(path = "{meetingRoomId}")
    public ApiResult<MeetingRoomResponse> modify(@PathVariable Long meetingRoomId,
        @RequestBody ModifyMeetingRoomRequest modifyRequest) {
        return ok(meetingRoomService.modify(Id.of(MeetingRoom.class, meetingRoomId),
            modifyRequest));
    }

}
