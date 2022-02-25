package com.m2rs.core.document.utils;

import com.google.gson.Gson;
import com.m2rs.core.document.constant.DocUrl;

public interface CodeLinkUtils {

    Gson GSON = new Gson();

    public static String generateLinkCode(DocUrl docUrl) {
        return String.format(
            "link:common/%s.html[%s %s,role=\"popup\"]", docUrl.getPageId(), docUrl.getText(),
            "코드");
    }

}
