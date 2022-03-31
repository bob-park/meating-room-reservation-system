package com.m2rs.meetingroomservice.commons.field.meetingroom.reservation;

import static com.m2rs.core.document.generator.DocumentFormatGenerator.generate;
import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public class MeetingRoomReservationRequestParamFields {

    public static final ParameterDescriptor[] SEARCH_REQUEST = {
        parameterWithName("startDateFrom")
            .description("시작 날짜(from)")
            .attributes(generateType(JsonFieldType.STRING),
                generate("yyyy-MM-dd"))
            .optional(),
        parameterWithName("startDateTo")
            .description("끝 날짜(to)")
            .attributes(generateType(JsonFieldType.STRING),
                generate("yyyy-MM-dd"))
            .optional(),
        parameterWithName("userId")
            .description("예약 사용자 아이디")
            .attributes(generateType(JsonFieldType.NUMBER))
            .optional(),
    };

}
