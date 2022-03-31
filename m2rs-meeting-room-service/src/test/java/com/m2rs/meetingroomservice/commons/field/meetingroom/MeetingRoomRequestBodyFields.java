package com.m2rs.meetingroomservice.commons.field.meetingroom;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MeetingRoomRequestBodyFields {

    public static final FieldDescriptor[] CREATE_REQUEST_FIELDS = {
        fieldWithPath("comId")
            .description("회사 아이디")
            .type(JsonFieldType.NUMBER),
        fieldWithPath("name")
            .description("회의실 이름")
            .type(JsonFieldType.STRING)
    };

}
