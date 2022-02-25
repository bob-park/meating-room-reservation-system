package com.m2rs.core.commons.model.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Getter
public class PageResponse<T> {

    private final long totalCount;
    private final long page;
    private final long size;
    private final long firstPage;
    private final long lastPage;
    private final List<T> contents;

    @Builder
    private PageResponse(long totalCount, long page, long size, long firstPage, long lastPage,
        List<T> contents) {
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
        this.firstPage = firstPage;
        this.lastPage = lastPage;

        this.contents = defaultIfNull(contents, Collections.emptyList());
    }
}
