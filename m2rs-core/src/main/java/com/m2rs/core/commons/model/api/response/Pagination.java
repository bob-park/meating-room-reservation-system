package com.m2rs.core.commons.model.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Pagination {

    private final long totalCount;
    private final long page;
    private final long size;
    private final long firstPage;
    private final long lastPage;

    @Builder
    private Pagination(long totalCount, long page, long size, long firstPage, long lastPage) {
        this.totalCount = totalCount;
        this.page = page;
        this.size = size;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }
}
