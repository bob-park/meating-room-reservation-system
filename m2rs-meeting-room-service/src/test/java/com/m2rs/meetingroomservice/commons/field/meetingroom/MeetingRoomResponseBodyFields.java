package com.m2rs.meetingroomservice.commons.field.meetingroom;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MeetingRoomResponseBodyFields {

    public static final FieldDescriptor[] MEETING_ROOM_RESPONSE = {
        fieldWithPath("id").description("회의실 아이디").type(JsonFieldType.NUMBER),
        fieldWithPath("comId").description("회사 아이디").type(JsonFieldType.NUMBER),
        fieldWithPath("name").description("회의실 이름").type(JsonFieldType.NUMBER),
        fieldWithPath("isActive").description("회의실 활성화 여부").type(JsonFieldType.NUMBER),
        fieldWithPath("createdDate").description("회의실 생성일").type(JsonFieldType.NUMBER),
        fieldWithPath("lastModifiedDate").description("회의실 수정일").type(JsonFieldType.NUMBER)
            .optional(),

    };

}
