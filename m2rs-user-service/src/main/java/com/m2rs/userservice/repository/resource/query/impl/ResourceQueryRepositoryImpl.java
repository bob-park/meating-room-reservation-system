package com.m2rs.userservice.repository.resource.query.impl;

import static com.m2rs.userservice.model.entity.QResource.resource;
import static com.m2rs.userservice.model.entity.QRole.role;

import com.m2rs.userservice.model.entity.Resource;
import com.m2rs.userservice.repository.resource.query.ResourceQueryRepository;
import com.m2rs.userservice.type.ResourceType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

public class ResourceQueryRepositoryImpl implements ResourceQueryRepository {

    private final JPAQueryFactory query;

    public ResourceQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Resource> findAllUrlResources() {

        return query.selectFrom(resource)
            .join(resource.roleSet, role).fetchJoin()
            .where(resource.resourceType.eq(ResourceType.URL))
            .orderBy(resource.orderNum.desc())
            .fetch();
    }
}
