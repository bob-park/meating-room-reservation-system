package com.m2rs.userservice.repository.company.query.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import com.google.common.collect.Lists;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.repository.company.query.CompanyQueryRepository;
import com.m2rs.userservice.repository.company.query.SearchCompanyQueryCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.m2rs.userservice.model.entity.QCompany.company;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {

    private final JPAQueryFactory query;

    public CompanyQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Company> search(SearchCompanyQueryCondition condition, Pageable pageable) {

        List<Company> contents = query
            .selectFrom(company)
            .where(mappingCondition(condition))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(getAllOrderSpecifiers(pageable.getSort()))
            .fetch();

        JPAQuery<Long> countQuery = query.select(company.id.count())
            .from(company)
            .where(mappingCondition(condition));

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    private Predicate mappingCondition(SearchCompanyQueryCondition condition) {

        BooleanBuilder builder = new BooleanBuilder();

        builder
            .and(containName(condition.getName()))
            .and(afterCreatedDate(condition.getCreatedDateFrom()))
            .and(beforeCreatedDate(condition.getCreatedDateTo()));

        return builder;
    }

    private BooleanExpression containName(String name) {
        return isNotEmpty(name) ? company.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression afterCreatedDate(LocalDateTime from) {

        if (from == null) {
            return null;
        }

        return company.createdDate.after(from.toLocalDate().atStartOfDay());
    }

    private BooleanExpression beforeCreatedDate(LocalDateTime to) {

        if (to == null) {
            return null;
        }

        return company.createdDate.before(to.toLocalDate().atTime(LocalTime.MAX));
    }

    // == sort == //
    private OrderSpecifier<?>[] getAllOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orders = Lists.newArrayList();

        for (Sort.Order order : sort) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty().toLowerCase()) {
                case "name":
                    orders.add(new OrderSpecifier<>(direction, company.name));
                    break;

                case "created_date":
                    orders.add(new OrderSpecifier<>(direction, company.createdDate));
                    break;

                case "last_modified_date":
                    orders.add(new OrderSpecifier<>(direction, company.lastModifiedDate));
                    break;

                default:
                    orders.add(new OrderSpecifier<>(direction, company.id));
                    break;
            }
        }

        return orders.toArray(new OrderSpecifier[]{});
    }
}
