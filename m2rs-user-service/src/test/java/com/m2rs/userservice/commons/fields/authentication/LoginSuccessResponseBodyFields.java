package com.m2rs.userservice.commons.fields.authentication;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class LoginSuccessResponseBodyFields {

    public static final FieldDescriptor[] LOGIN_SUCCESS = {
        fieldWithPath("token")
            .description("액세스 토큰")
            .type(JsonFieldType.STRING),
        subsectionWithPath("user.id")
            .description("사용자 아이디")
            .type(JsonFieldType.NUMBER),
        subsectionWithPath("user.comId")
            .description("사용자 회사 아이디")
            .type(JsonFieldType.NUMBER)
            .optional(),
        subsectionWithPath("user.departmentId")
            .description("사용자 부서 아이디")
            .type(JsonFieldType.NUMBER)
            .optional(),
        subsectionWithPath("user.email")
            .description("사용자 email")
            .type(JsonFieldType.STRING),
        subsectionWithPath("user.name")
            .description("사용자 이름")
            .type(JsonFieldType.STRING),
        subsectionWithPath("user.roleTypes")
            .description("사용자 권한 목록")
            .type(JsonFieldType.ARRAY),
        subsectionWithPath("user.phone")
            .description("사용자 전화번호")
            .type(JsonFieldType.STRING)
            .optional(),
        subsectionWithPath("user.cellPhone")
            .description("사용자 휴대번호")
            .type(JsonFieldType.STRING)
            .optional(),
        subsectionWithPath("user.createdDate")
            .description("사용자 생성일")
            .type(JsonFieldType.STRING),
        subsectionWithPath("user.lastModifiedDate")
            .description("사용자 수정일")
            .type(JsonFieldType.STRING)
            .optional()
    };

}
