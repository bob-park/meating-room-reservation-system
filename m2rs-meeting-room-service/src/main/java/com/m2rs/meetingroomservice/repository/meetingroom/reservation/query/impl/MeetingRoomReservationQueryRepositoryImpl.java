package com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.impl;

import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;

public class MeetingRoomReservationQueryRepositoryImpl implements
    MeetingRoomReservationQueryRepository {

    private final JPAQueryFactory query;

    public MeetingRoomReservationQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    // == mapping condition == //
}
