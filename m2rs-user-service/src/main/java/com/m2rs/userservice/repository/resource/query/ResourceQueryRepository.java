package com.m2rs.userservice.repository.resource.query;

import com.m2rs.userservice.model.entity.Resource;
import java.util.List;

public interface ResourceQueryRepository {

    List<Resource> findAllUrlResources();

}
