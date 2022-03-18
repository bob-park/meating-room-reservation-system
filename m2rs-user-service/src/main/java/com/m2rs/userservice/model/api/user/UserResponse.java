package com.m2rs.userservice.model.api.user;


import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.m2rs.core.security.model.RoleType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final Long comId;
    private final Long departmentId;
    private final String email;
    private final String name;

    private final List<RoleType> roleTypes;

    private final String phone;
    private final String cellPhone;

    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    private UserResponse(Long id, Long comId, Long departmentId, String email,
        String name,
        List<RoleType> roleTypes,
        String phone, String cellPhone, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {
        this.id = id;
        this.comId = comId;
        this.departmentId = departmentId;
        this.email = email;
        this.name = name;

        this.roleTypes = defaultIfNull(roleTypes, Collections.emptyList());
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
