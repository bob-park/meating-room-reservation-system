package com.m2rs.userservice.commons.fields.user;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public class UserRequestParamField {

    public static final ParameterDescriptor[] SEARCH_REQUEST_PARAMETERS = {
        parameterWithName("departmentId")
            .description("부서 아이디")
            .attributes(generateType(JsonFieldType.NUMBER))
            .optional(),
        parameterWithName("email")
            .description("사용자 email")
            .attributes(generateType(JsonFieldType.STRING))
            .optional(),
        parameterWithName("name")
            .description("사용자 이름")
            .attributes(generateType(JsonFieldType.STRING))
            .optional(),
    };

}
