package com.m2rs.userservice.repository.resource;

import com.m2rs.userservice.model.entity.Resource;
import com.m2rs.userservice.repository.resource.query.ResourceQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceQueryRepository {

}
