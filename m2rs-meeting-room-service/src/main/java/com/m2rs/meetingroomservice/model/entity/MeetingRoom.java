package com.m2rs.meetingroomservice.model.entity;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.meetingroomservice.model.entity.base.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "meeting_rooms")
public class MeetingRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long comId;
    private String name;
    private Boolean isActive;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingRoom")
    private List<MeetingRoomReservation> meetingRoomReservationList = new ArrayList<>();

    @Builder
    private MeetingRoom(Long id, Long comId, String name, Boolean isActive) {
        this.id = id;
        this.comId = comId;
        this.name = name;
        this.isActive = defaultIfNull(isActive, true);

        this.createdDate = LocalDateTime.now();
    }

    public void modifyName(String updateName) {
        this.name = defaultIfNull(updateName, this.name);
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addReservation(MeetingRoomReservation reservation) {
        meetingRoomReservationList.add(reservation);
    }
}
