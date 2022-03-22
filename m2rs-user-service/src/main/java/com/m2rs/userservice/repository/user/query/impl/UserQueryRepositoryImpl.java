package com.m2rs.userservice.repository.user.query.impl;

import static com.m2rs.userservice.model.entity.QCompany.company;
import static com.m2rs.userservice.model.entity.QDepartment.department;
import static com.m2rs.userservice.model.entity.QRole.role;
import static com.m2rs.userservice.model.entity.QUser.user;
import static com.m2rs.userservice.model.entity.QUserRoles.userRoles;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.QCompany;
import com.m2rs.userservice.model.entity.QDepartment;
import com.m2rs.userservice.model.entity.User;
import com.m2rs.userservice.repository.user.query.UserQueryRepository;
import com.m2rs.userservice.repository.user.query.SearchUserQueryCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.ObjectUtils;
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
    public Page<User> search(SearchUserQueryCondition condition, Pageable pageable) {

        List<User> contents = query.selectFrom(user)
            .join(user.userRoles, userRoles).fetchJoin()
            .join(userRoles.role, role).fetchJoin()
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

    @Override
    public Optional<User> getUser(Id<Company, Long> comId, Id<User, Long> id) {

        User result = query.selectFrom(user)
            .join(user.department, department).fetchJoin()
            .join(department.company, company).fetchJoin()
            .where(user.id.eq(id.value()), user.department.company.id.eq(comId.value()))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public boolean isExistEmail(Id<Company, Long> comId, String email) {

        Long count = query.select(user.id.count())
            .from(user)
            .where(user.email.eq(email))
            .fetchOne();

        return defaultIfNull(count, 0L) > 0;
    }

    // == mapping condition == //
    private Predicate mappingCondition(SearchUserQueryCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqId(condition.getId()))
            .and(eqComId(condition.getComId()))
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

    private BooleanExpression eqComId(Long comId) {
        return comId != null ? user.department.company.id.eq(comId) : null;
    }
}
