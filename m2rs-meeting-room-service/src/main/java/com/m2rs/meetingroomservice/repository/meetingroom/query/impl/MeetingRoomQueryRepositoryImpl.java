package com.m2rs.meetingroomservice.repository.meetingroom.query.impl;

import static com.m2rs.meetingroomservice.model.entity.QMeetingRoom.meetingRoom;

import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomQueryRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.query.SearchMeetingRoomQueryCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;

public class MeetingRoomQueryRepositoryImpl implements MeetingRoomQueryRepository {

    private final JPAQueryFactory query;

    public MeetingRoomQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MeetingRoom> search(SearchMeetingRoomQueryCondition condition) {
        return query.selectFrom(meetingRoom)
            .where(mappingCondition(condition))
            .orderBy(meetingRoom.name.asc())
            .fetch();
    }

    // == mapping condition == //
    private Predicate mappingCondition(SearchMeetingRoomQueryCondition condition) {

        BooleanBuilder bool = new BooleanBuilder();

        bool.and(eqComId(condition.getComId()))
            .and(eqIsActive(condition.getIsActive()))
            .and(containName(condition.getName()));

        return bool;
    }

    private BooleanExpression eqComId(Long comId) {
        return comId != null ? meetingRoom.comId.eq(comId) : null;
    }

    private BooleanExpression eqIsActive(Boolean isActive) {
        return isActive != null ? meetingRoom.isActive.eq(isActive) : null;
    }

    private BooleanExpression containName(String name) {
        return StringUtils.isNotBlank(name) ? meetingRoom.name.containsIgnoreCase(name) : null;
    }
}
