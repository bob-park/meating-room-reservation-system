package com.m2rs.meetingroomservice.commons.field.meetingroom.reservation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.m2rs.core.document.generator.DocumentFormatGenerator;
import java.time.format.DateTimeFormatter;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MeetingRoomReservationRequestBodyFields {

    public static final FieldDescriptor[] CREATE_RESERVATION = {
        fieldWithPath("title").description("회의 제목")
            .type(JsonFieldType.STRING),
        fieldWithPath("description").description("회의 설명")
            .type(JsonFieldType.STRING)
            .optional(),
        fieldWithPath("numberOfUsers").description("회의실 사용자 수")
            .type(JsonFieldType.NUMBER)
            .optional(),
        fieldWithPath("startDate").description("시작 시간")
            .type(JsonFieldType.STRING)
            .attributes(DocumentFormatGenerator.generate(DateTimeFormatter.ISO_DATE_TIME)),
        fieldWithPath("endDate").description("종료 시간")
            .type(JsonFieldType.STRING)
    };

    public static final FieldDescriptor[] MODIFY_RESERVATION = {
        fieldWithPath("title").description("회의 제목")
            .type(JsonFieldType.STRING)
            .optional(),
        fieldWithPath("description").description("회의 설명")
            .type(JsonFieldType.STRING)
            .optional(),
        fieldWithPath("numberOfUsers").description("회의실 사용자 수")
            .type(JsonFieldType.NUMBER)
            .optional(),
        fieldWithPath("startDate").description("시작 시간")
            .type(JsonFieldType.STRING),
        fieldWithPath("endDate").description("종료 시간")
            .type(JsonFieldType.STRING)
    };


}
