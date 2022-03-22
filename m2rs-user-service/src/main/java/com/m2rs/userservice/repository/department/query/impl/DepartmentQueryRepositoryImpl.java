package com.m2rs.userservice.repository.department.query.impl;

import static com.m2rs.userservice.model.entity.QCompany.company;
import static com.m2rs.userservice.model.entity.QDepartment.department;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.Department;
import com.m2rs.userservice.repository.department.query.DepartmentQueryRepository;
import com.m2rs.userservice.repository.department.query.SearchDepartmentQueryCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;

public class DepartmentQueryRepositoryImpl implements DepartmentQueryRepository {

    private final JPAQueryFactory query;

    public DepartmentQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Department> search(SearchDepartmentQueryCondition condition) {
        return query.selectFrom(department)
            .join(department.company, company).fetchJoin()
            .where(mappingCondition(condition))
            .orderBy(department.name.asc())
            .fetch();
    }

    @Override
    public Optional<Department> getDepartment(Id<Company, Long> comId,
        Id<Department, Long> departmentId) {

        Department result = query.selectFrom(department)
            .where(department.id.eq(departmentId.value()),
                department.company.id.eq(comId.value()))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    // == mapping condition == //
    private Predicate mappingCondition(SearchDepartmentQueryCondition condition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqComId(condition.getComId()))
            .and(eqDepartmentId(condition.getDepartmentId()))
            .and(containsName(condition.getName()));

        return builder;
    }

    private BooleanExpression eqComId(Long comId) {
        return comId != null ? department.company.id.eq(comId) : null;
    }

    private BooleanExpression eqDepartmentId(Long departmentId) {
        return departmentId != null ? department.id.eq(departmentId) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? department.name.containsIgnoreCase(name) : null;
    }

}
