package com.m2rs.userservice.model.entity;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.userservice.model.entity.base.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<>();

    @Builder
    private Company(Long id, String name, String logoPath, LocalDateTime createdDate,
        LocalDateTime lastModifiedDate) {

        checkArgument(isNotEmpty(name), "name must be provided.");

        this.id = id;
        this.name = name;
        this.logoPath = logoPath;

        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;

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

    public void addUser(User user){
        this.users.add(user);
    }
}
