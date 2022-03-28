package com.m2rs.userservice.commons.fields.department;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public class DepartmentRequestParamField {

    public static final ParameterDescriptor[] GET_DEPARTMENT_LIST = {
        parameterWithName("departmentId")
            .description("부서 아이디")
            .attributes(generateType(JsonFieldType.NUMBER))
            .optional(),
        parameterWithName("name")
            .description("부서 이름")
            .attributes(generateType(JsonFieldType.STRING))
            .optional()
    };

}
