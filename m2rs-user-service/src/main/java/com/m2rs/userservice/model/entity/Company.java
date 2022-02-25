package com.m2rs.userservice.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.m2rs.userservice.model.entity.base.BaseTimeEntity;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String logoPath;

    @Builder
    private Company(Long id, String name, String logoPath) {

        checkArgument(isNotEmpty(name), "name must be provided.");

        this.id = id;
        this.name = name;
        this.logoPath = logoPath;

    }

    public void addLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public void changeName(String changeName) {

        if (isEmpty(changeName)) {
            return;
        }

        this.name = changeName;
    }
}
