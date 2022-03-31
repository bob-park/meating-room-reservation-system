package com.m2rs.userservice.commons.fields.authentication;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class LoginRequestBodyFields {

    public static final FieldDescriptor[] LOGIN_REQUEST = {
        fieldWithPath("email")
            .description("email")
            .type(JsonFieldType.STRING),
        fieldWithPath("password")
            .description("password")
            .type(JsonFieldType.STRING)
    };

}
