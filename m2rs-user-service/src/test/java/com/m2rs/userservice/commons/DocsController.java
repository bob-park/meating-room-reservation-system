package com.m2rs.userservice.commons;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.commons.model.api.response.Pagination;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

@RestController
@RequestMapping("docs")
public class DocsController {

    @GetMapping
    public ApiResult<List<Boolean>> docs() {

        return ok(Collections.singletonList(true), Pagination.builder()
            .size(10)
            .totalCount(1)
            .lastPage(1)
            .build());
    }

}
