package com.m2rs.userservice.commons.fields.department;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class DepartmentResponseField {

    public static final FieldDescriptor[] DEPARTMENT_RESPONSE = {
        fieldWithPath("id")
            .type(JsonFieldType.NUMBER)
            .description("부서 아이디"),
        subsectionWithPath("company.id")
            .type(JsonFieldType.NUMBER)
            .description("회사 아이디"),
        subsectionWithPath("company.name")
            .type(JsonFieldType.STRING)
            .description("회사 이름")
            .optional(),
        subsectionWithPath("company.logPath")
            .type(JsonFieldType.STRING)
            .description("회사 로고 경로")
            .optional(),
        subsectionWithPath("company.createdDate")
            .type(JsonFieldType.STRING)
            .description("회사 생성일"),
        subsectionWithPath("company.lastModifiedDate")
            .type(JsonFieldType.STRING)
            .description("회사 수정일")
            .optional(),
        fieldWithPath("name")
            .type(JsonFieldType.STRING)
            .description("부서 이름")
            .optional(),
        fieldWithPath("createdDate")
            .type(JsonFieldType.STRING)
            .description("부서 생성일"),
        fieldWithPath("lastModifiedDate")
            .type(JsonFieldType.STRING)
            .description("부서 수정일")
            .optional()
    };

}
