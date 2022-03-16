package com.m2rs.meetingroomservice.model.entity;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "meetingRoomReservation")
public class MeetingRoomReservation extends BaseUserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mr_id")
    private MeetingRoom meetingRoom;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    private MeetingRoomReservation(Long id,
        MeetingRoom meetingRoom, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {

        meetingRoom.addReservation(this);

        this.meetingRoom = meetingRoom;
    }
}
