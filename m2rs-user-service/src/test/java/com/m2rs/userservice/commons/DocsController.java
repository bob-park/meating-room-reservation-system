package com.m2rs.userservice.commons;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2rs.core.commons.model.api.response.ApiResult;

import static com.m2rs.core.commons.model.api.response.ApiResult.OK;

@RestController
@RequestMapping("docs")
public class DocsController {

    @GetMapping
    public ApiResult<Boolean> docs() {
        return OK(true);
    }

}
