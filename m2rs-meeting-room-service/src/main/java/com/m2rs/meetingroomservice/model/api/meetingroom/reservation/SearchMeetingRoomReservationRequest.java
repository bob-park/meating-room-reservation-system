package com.m2rs.meetingroomservice.model.api.meetingroom.reservation;

import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.SearchMeetingRoomReservationQueryCondition;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter
public class SearchMeetingRoomReservationRequest {

    @DateTimeFormat(iso = ISO.DATE)
    private final LocalDate startDateFrom;

    @DateTimeFormat(iso = ISO.DATE)
    private final LocalDate startDateTo;

    private final Long userId;

    @Builder
    private SearchMeetingRoomReservationRequest(LocalDate startDateFrom, LocalDate startDateTo,
        Long userId) {
        this.startDateFrom = startDateFrom;
        this.startDateTo = startDateTo;
        this.userId = userId;
    }

    public SearchMeetingRoomReservationQueryCondition getQueryCondition(
        Id<MeetingRoom, Long> mrId) {
        return SearchMeetingRoomReservationQueryCondition.builder()
            .startDateFrom(startDateFrom)
            .startDateTo(startDateTo)
            .mrId(mrId.value())
            .userId(userId)
            .build();
    }

}
