package com.m2rs.core.commons.model.service.page;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import com.m2rs.core.commons.model.api.response.Pagination;

@Getter
public class ServicePage<T> {

    public final Pagination page;
    private final List<T> contents;

    @Builder
    private ServicePage(Pagination page, List<T> contents) {
        this.page = page;
        this.contents = contents;
    }
}
