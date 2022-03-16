package com.m2rs.meetingroomservice.repository.meetingroom.reservation;

import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomReservationRepository extends
    JpaRepository<MeetingRoomReservation, Long>, MeetingRoomReservationQueryRepository {

}
