package com.m2rs.meetingroomservice.commons.field.meetingroom;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

public class MeetingRoomRequestParamFields {

    public static final ParameterDescriptor[] SEARCH_REQUEST = {
        parameterWithName("comId")
            .description("회사 아이디")
            .attributes(generateType(JsonFieldType.NUMBER))
            .optional(),
        parameterWithName("isActive")
            .description("회의실 활성화 여부")
            .attributes(generateType(JsonFieldType.BOOLEAN))
            .optional(),
        parameterWithName("name")
            .description("회의실 이름")
            .attributes(generateType(JsonFieldType.STRING))
            .optional(),
    };

}
