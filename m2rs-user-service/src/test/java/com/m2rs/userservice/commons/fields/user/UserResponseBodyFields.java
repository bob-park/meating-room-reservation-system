package com.m2rs.userservice.commons.fields.user;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserResponseBodyFields {

    public static final FieldDescriptor[] USER_RESPONSE = {
        fieldWithPath("id").description("사용자 아이디").type(JsonFieldType.NUMBER),
        fieldWithPath("comId").description("회사 아이디").type(JsonFieldType.NUMBER).optional(),
        fieldWithPath("departmentId").description("부서 아이디").type(JsonFieldType.NUMBER).optional(),
        fieldWithPath("email").description("사용자 이메일").type(JsonFieldType.STRING).optional(),
        fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING),
        fieldWithPath("roleTypes").description("사용자 권한 목록").type(JsonFieldType.ARRAY),
        fieldWithPath("phone").description("사용자 전화번호").type(JsonFieldType.STRING).optional(),
        fieldWithPath("cellPhone").description("사용자 휴대번호").type(JsonFieldType.STRING).optional(),
        fieldWithPath("createdDate").description("사용자 생성일").type(JsonFieldType.STRING),
        fieldWithPath("lastModifiedDate").description("사용자 수정일").type(JsonFieldType.STRING).optional()
    };

}
