package com.m2rs.userservice.repository.role.query.impl;

import static com.m2rs.userservice.model.entity.QRole.role;

import com.m2rs.userservice.model.entity.Role;
import com.m2rs.userservice.repository.role.query.RoleQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class RoleQueryRepositoryImpl implements RoleQueryRepository {

    private final JPAQueryFactory query;

    public RoleQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Role> searchAll() {
        return query.selectFrom(role)
            .orderBy(role.parentRole.id.asc().nullsFirst())
            .fetch();
    }
}
