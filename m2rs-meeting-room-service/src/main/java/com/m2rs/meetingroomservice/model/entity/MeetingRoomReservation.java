package com.m2rs.meetingroomservice.model.entity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.google.common.base.Preconditions;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.base.BaseUserEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "meeting_room_reservations")
public class MeetingRoomReservation extends BaseUserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mr_id")
    private MeetingRoom meetingRoom;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    private MeetingRoomReservation(Long id,
        MeetingRoom meetingRoom, LocalDateTime startDate, LocalDateTime endDate) {

        checkNotNull(meetingRoom, "meetingRoom must be provided.");
        checkNotNull(startDate, "startDate must be provided.");
        checkNotNull(endDate, "endDate must be provided.");

        checkArgument(startDate.isBefore(endDate), "startDate must be less than endDate.");

        this.id = id;
        this.meetingRoom = meetingRoom;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void modify(ModifyMeetingRoomReservationRequest modifyRequest) {
        this.startDate = modifyRequest.getStartDate();
        this.endDate = modifyRequest.getEndDate();
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {

        meetingRoom.addReservation(this);

        this.meetingRoom = meetingRoom;
    }
}
