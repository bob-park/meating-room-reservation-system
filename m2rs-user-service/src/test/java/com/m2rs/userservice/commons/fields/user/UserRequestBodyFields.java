package com.m2rs.userservice.commons.fields.user;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class UserRequestBodyFields {

    public static FieldDescriptor[] CREATE_USER_REQUEST = {
        fieldWithPath("departmentId").description("부서 아이디").type(JsonFieldType.NUMBER),
        fieldWithPath("email").description("사용자 이메일").type(JsonFieldType.STRING),
        fieldWithPath("password").description("사용자 패스워드").type(JsonFieldType.STRING),
        fieldWithPath("name").description("사용자 이름").type(JsonFieldType.STRING),
        fieldWithPath("phone").description("사용자 전화번호").type(JsonFieldType.STRING).optional(),
        fieldWithPath("cellPhone").description("사용자 휴대번호").type(JsonFieldType.STRING).optional()
    };

}
