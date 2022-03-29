package com.m2rs.userservice.model.api.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CheckExistEmailResponse {

    private final Boolean exist;

    @Builder
    private CheckExistEmailResponse(Boolean exist) {
        this.exist = exist;
    }
}
