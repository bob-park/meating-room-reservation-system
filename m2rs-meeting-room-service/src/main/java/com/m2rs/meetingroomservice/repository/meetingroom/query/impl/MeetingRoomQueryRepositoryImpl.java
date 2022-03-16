package com.m2rs.meetingroomservice.repository.meetingroom.query.impl;

import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomQueryRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class MeetingRoomQueryRepositoryImpl implements MeetingRoomQueryRepository {

    private final JPAQueryFactory query;

    public MeetingRoomQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MeetingRoom> search(MeetingRoomSearchCondition condition) {
        return null;
    }
}
