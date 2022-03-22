package com.m2rs.userservice.controller.admin.resource;

import static com.m2rs.core.commons.model.api.response.ApiResult.ok;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.userservice.service.resource.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/resource")
public class AdminResourceController {

    private final ResourceService resourceService;

    @PostMapping(path = "reload")
    public ApiResult<Boolean> reload() {
        return ok(resourceService.reloadResource());
    }

}
