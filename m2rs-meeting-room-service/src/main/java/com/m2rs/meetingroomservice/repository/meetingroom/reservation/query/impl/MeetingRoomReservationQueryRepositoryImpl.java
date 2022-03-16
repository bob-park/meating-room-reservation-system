package com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.impl;

import static com.m2rs.meetingroomservice.model.entity.QMeetingRoomReservation.meetingRoomReservation;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationQueryRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.EntityManager;

public class MeetingRoomReservationQueryRepositoryImpl implements
    MeetingRoomReservationQueryRepository {

    private final JPAQueryFactory query;

    public MeetingRoomReservationQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<MeetingRoomReservation> search(MeetingRoomReservationSearchCondition condition) {
        return query.selectFrom(meetingRoomReservation)
            .where(mappingCondition(condition))
            .orderBy(meetingRoomReservation.startDate.asc())
            .fetch();
    }

    @Override
    public boolean checkAvailableReservation(Id<MeetingRoom, Long> mrId, LocalDateTime startDate,
        LocalDateTime endDate) {

        Long count = query.select(meetingRoomReservation.id.count())
            .from(meetingRoomReservation)
            .where(meetingRoomReservation.meetingRoom.id.eq(mrId.value())
                .and(meetingRoomReservation.startDate.before(endDate))
                .and(meetingRoomReservation.endDate.after(startDate)))
            .fetchOne();

        return defaultIfNull(count, 0L) < 1;
    }

    // == mapping condition == //
    private Predicate mappingCondition(MeetingRoomReservationSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(afterStartDateFrom(condition.getStartDateFrom()))
            .and(beforeStartDateTo(condition.getStartDateTo()))
            .and(eqMrId(condition.getMrId()))
            .and(eqUserId(condition.getUserId()));

        return builder;
    }

    private BooleanExpression afterStartDateFrom(LocalDate from) {
        return from != null ?
            meetingRoomReservation.startDate.goe(from.atStartOfDay()) : null;
    }

    private BooleanExpression beforeStartDateTo(LocalDate to) {
        return to != null ?
            meetingRoomReservation.startDate.loe(to.atTime(LocalTime.MAX)) : null;
    }

    private BooleanExpression eqMrId(Long mrId) {
        return mrId != null ? meetingRoomReservation.meetingRoom.id.eq(mrId) : null;
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? meetingRoomReservation.userId.eq(userId) : null;
    }

    private BooleanExpression afterStartDate(LocalDateTime startDate) {
        return startDate != null ? meetingRoomReservation.startDate.goe(startDate) : null;
    }

    private BooleanExpression beforeEndDate(LocalDateTime endDate) {
        return endDate != null ? meetingRoomReservation.endDate.loe(endDate) : null;
    }
}
