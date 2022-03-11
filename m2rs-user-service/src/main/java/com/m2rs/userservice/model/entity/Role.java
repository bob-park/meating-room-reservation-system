package com.m2rs.userservice.model.entity;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.entity.base.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class Role extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Role parentRole;

    @Enumerated(EnumType.STRING)
    private RoleType rolesName;

    private String name;


}
