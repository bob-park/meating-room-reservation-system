package com.m2rs.meetingroomservice.commons.field.meetingroom.reservation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.m2rs.core.document.generator.DocumentDefaultInputGenerator;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MeetingRoomReservationResponseBodyFields {

    public static final FieldDescriptor[] RESERVATION_RESPONSE = {
        fieldWithPath("id")
            .description("예약 아이디")
            .type(JsonFieldType.NUMBER),
        fieldWithPath("userId")
            .description("예약자 아이디")
            .type(JsonFieldType.NUMBER),
        fieldWithPath("mrId")
            .description("회의실 아이디")
            .type(JsonFieldType.NUMBER),
        fieldWithPath("title")
            .description("회의 제목")
            .type(JsonFieldType.STRING),
        fieldWithPath("description")
            .description("회의 설명")
            .type(JsonFieldType.STRING)
            .optional(),
        fieldWithPath("numberOfUsers")
            .description("회의실 사용자 수")
            .type(JsonFieldType.NUMBER)
            .attributes(DocumentDefaultInputGenerator.generate("2")),
        fieldWithPath("startDate")
            .description("예약 시작 시간")
            .type(JsonFieldType.STRING),
        fieldWithPath("endDate")
            .description("예약 종료 시간")
            .type(JsonFieldType.STRING),
        fieldWithPath("createdDate")
            .description("예약 생성일")
            .type(JsonFieldType.STRING),
        fieldWithPath("lastModifiedDate")
            .description("예약 수정일")
            .type(JsonFieldType.STRING)
            .optional()
    };

}
