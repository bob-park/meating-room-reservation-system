package com.m2rs.userservice.repository.user.query.impl;

import static com.m2rs.userservice.model.entity.QUser.user;

import com.m2rs.userservice.model.entity.QUser;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.query.UserQueryRepository;
import com.m2rs.userservice.repository.user.query.UserSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory query;

    public UserQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<User> search(UserSearchCondition condition, Pageable pageable) {

        List<User> contents = query.selectFrom(user)
            .where(mappingCondition(condition))
            .orderBy(user.name.asc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .fetch();

        JPAQuery<Long> countQuery = query.select(user.id.count())
            .from(user)
            .where(mappingCondition(condition));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    // == mapping condition == //
    private Predicate mappingCondition(UserSearchCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqId(condition.getId()))
            .and(eqDepartmentId(condition.getDepartmentId()))
            .and(eqEmail(condition.getEmail()))
            .and(containName(condition.getName()));

        return builder;
    }

    private BooleanExpression eqId(Long id) {
        return id != null ? user.id.eq(id) : null;
    }

    private BooleanExpression eqDepartmentId(Long departmentId) {
        return departmentId != null ? user.department.id.eq(departmentId) : null;
    }

    private BooleanExpression eqEmail(String email) {
        return StringUtils.isNotBlank(email) ? user.email.eq(email) : null;
    }

    private BooleanExpression containName(String name) {
        return StringUtils.isNotBlank(name) ? user.name.containsIgnoreCase(name) : null;
    }
}
