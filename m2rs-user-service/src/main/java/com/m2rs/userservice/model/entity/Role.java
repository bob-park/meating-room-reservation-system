package com.m2rs.userservice.model.entity;

import com.m2rs.core.security.model.RoleType;
import com.m2rs.userservice.model.entity.base.BaseTimeEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles")
public class Role extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Role parentRole;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentRole")
    private List<Role> childRoles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleType rolesName;

    private String rolesDescription;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<UserRoles> userRoles = new ArrayList<>();

    @Builder
    private Role(Long id, RoleType rolesName, String rolesDescription) {
        this.id = id;
        this.rolesName = rolesName;
        this.rolesDescription = rolesDescription;
    }
}
