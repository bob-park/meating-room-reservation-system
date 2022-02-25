package com.m2rs.userservice.commons.fields.company;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class CompanyResponseField {

    public static final FieldDescriptor[] COMPANY_RESPONSE = {
        fieldWithPath("id").type(JsonFieldType.NUMBER).description("회사 아이디"),
        fieldWithPath("name").type(JsonFieldType.STRING).description("회사 이름"),
        fieldWithPath("logoPath").type(JsonFieldType.STRING).description("회사 로고 경로"),
        fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성일"),
        fieldWithPath("lastModifiedDate").type(JsonFieldType.STRING).description("마지막 수정일"),
    };

}
