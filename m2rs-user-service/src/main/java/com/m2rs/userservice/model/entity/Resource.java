package com.m2rs.userservice.model.entity;

import com.m2rs.userservice.type.ResourceType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Getter
@ToString
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    private String resourceName;
    private String httpMethod;
    private int orderNum;

    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "resources_roles",
        joinColumns = @JoinColumn(name = "resource_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleSet = new HashSet<>();


}
