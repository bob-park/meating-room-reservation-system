package com.m2rs.userservice.model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.apache.commons.io.IOUtils;

import com.m2rs.core.commons.exception.ServiceRuntimeException;
import com.m2rs.userservice.model.entity.base.BaseTimeEntity;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "blob")
    private byte[] logo;

    @Builder
    private Company(Long id, String name, InputStream logo) {

        checkArgument(isNotEmpty(name), "name must be provided.");

        this.id = id;
        this.name = name;

        try {

            if (isNotEmpty(logo)) {
                this.logo = IOUtils.toByteArray(logo);
            }

        } catch (IOException e) {
            throw new ServiceRuntimeException(e.getMessage());
        }

    }
}
