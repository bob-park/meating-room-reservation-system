package com.m2rs.userservice.repository.user.query;

import com.m2rs.core.model.Id;
import com.m2rs.userservice.model.entity.Company;
import com.m2rs.userservice.model.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    Page<User> search(SearchUserQueryCondition condition, Pageable pageable);

    Optional<User> getUser(Id<Company, Long> comId, Id<User, Long> id);

    boolean isExistEmail(Id<Company, Long> comId, String email);

}
