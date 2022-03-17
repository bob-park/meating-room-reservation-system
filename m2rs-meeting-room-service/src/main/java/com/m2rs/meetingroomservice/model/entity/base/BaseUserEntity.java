package com.m2rs.meetingroomservice.model.entity.base;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseUserEntity extends BaseTimeEntity {

    @CreatedBy
    @Column(name = "user_id", updatable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }
}
