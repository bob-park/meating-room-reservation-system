package com.m2rs.core.document.constant;

public enum DocUrl {

    ;

    private final String pageId;
    private final String text;

    DocUrl(String pageId, String text) {
        this.pageId = pageId;
        this.text = text;
    }

    public String getPageId() {
        return pageId;
    }

    public String getText() {
        return text;
    }
}
