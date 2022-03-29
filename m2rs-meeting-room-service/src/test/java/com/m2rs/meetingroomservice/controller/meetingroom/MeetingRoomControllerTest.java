package com.m2rs.meetingroomservice.controller.meetingroom;

import static com.m2rs.core.document.generator.DocumentParamTypeGenerator.generateType;
import static com.m2rs.core.document.utils.SnippetUtils.customPathParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.customRequestParamFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.meetingroomservice.commons.field.meetingroom.MeetingRoomRequestBodyFields;
import com.m2rs.meetingroomservice.commons.field.meetingroom.MeetingRoomRequestParamFields;
import com.m2rs.meetingroomservice.commons.field.meetingroom.MeetingRoomResponseBodyFields;
import com.m2rs.meetingroomservice.controller.CommonControllerTest;
import com.m2rs.meetingroomservice.feign.client.UserServiceClient;
import com.m2rs.meetingroomservice.model.api.company.CompanyResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.ModifyMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

class MeetingRoomControllerTest extends CommonControllerTest {

    private static final String MEETING_ROOM_API = "/meeting/room";

    @MockBean
    MeetingRoomRepository meetingRoomRepository;

    @MockBean
    UserServiceClient userServiceClient;

    @BeforeEach
    void setup() {

        // mock company response
        CompanyResponse mockCompanyResponse =
            CompanyResponse.builder()
                .id(1L)
                .name("test-company")
                .createdDate(LocalDateTime.now())
                .build();

        // mock meeting room
        MeetingRoom mockActiveMeetingRoom =
            MeetingRoom.builder()
                .id(1L)
                .comId(1L)
                .name("test-meeting-room")
                .build();

        MeetingRoom mockInactiveMeetingRoom =
            MeetingRoom.builder()
                .id(2L)
                .comId(1L)
                .name("test-meeting-room")
                .isActive(false)
                .build();

        /*
         mock user service client
         */
        when(userServiceClient.getCompany(any())).thenReturn(ApiResult.ok(mockCompanyResponse));

        /*
         mock meeting room repository
         */
        when(meetingRoomRepository.save(any())).thenReturn(mockActiveMeetingRoom);
        when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(mockActiveMeetingRoom));
        when(meetingRoomRepository.findById(2L)).thenReturn(Optional.of(mockInactiveMeetingRoom));
        when(meetingRoomRepository.search(any())).thenReturn(
            Collections.singletonList(mockActiveMeetingRoom));



    }

    @Test
    @DisplayName("create meeting room test")
    void createMeetingRoomTest() throws Exception {
        CreateMeetingRoomRequest createRequest =
            CreateMeetingRoomRequest.builder()
                .comId(1L)
                .name("test-meeting-room")
                .build();

        mockMvc.perform(post(MEETING_ROOM_API)
                .headers(getDefaultHeaders())
                .content(toJson(createRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document.document(
                customRequestBodyFields(MeetingRoomRequestBodyFields.CREATE_REQUEST_FIELDS),
                customResponseBodyFields(MeetingRoomResponseBodyFields.MEETING_ROOM_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("mockMeetingRoomTestPathVariables")
    @DisplayName("get meeting room test")
    void getMeetingRoomTest(long mrId) throws Exception {
        mockMvc.perform(get(MEETING_ROOM_API + "/{mrId}", mrId)
                .headers(getDefaultHeaders()))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseBodyFields(MeetingRoomResponseBodyFields.MEETING_ROOM_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("mockMeetingRoomTestPathVariables")
    @DisplayName("modify meeting room test")
    void modifyMeetingRoomTest(long mrId) throws Exception {

        ModifyMeetingRoomRequest modifyRequest = new ModifyMeetingRoomRequest();

        modifyRequest.setName("test-meeting-room");

        mockMvc.perform(put(MEETING_ROOM_API + "/{mrId}", mrId)
                .headers(getDefaultHeaders())
                .content(toJson(modifyRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(fieldWithPath("name")
                    .description("회의실 이름")
                    .type(JsonFieldType.STRING)
                    .optional()),
                customResponseBodyFields(MeetingRoomResponseBodyFields.MEETING_ROOM_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("mockMeetingRoomTestPathVariables")
    @DisplayName("inactive meeting room test")
    void inactiveMeetingRoomTest(long mrId) throws Exception {

        mockMvc.perform(delete(MEETING_ROOM_API + "/{mrId}/inactive", mrId)
                .headers(getDefaultHeaders()))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER)))));
    }

    @ParameterizedTest
    @ValueSource(longs = 2)
    @DisplayName("active meeting room test")
    void activeMeetingRoomTest(long mrId) throws Exception {

        mockMvc.perform(put(MEETING_ROOM_API + "/{mrId}/active", mrId)
                .headers(getDefaultHeaders()))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customResponseBodyFields(MeetingRoomResponseBodyFields.MEETING_ROOM_RESPONSE)));
    }

    @Test
    @DisplayName("search meeting room test")
    void getMeetingRoomListTest() throws Exception {
        mockMvc.perform(get(MEETING_ROOM_API + "/list")
                .headers(getDefaultHeaders())
                .param("isActive", "true"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customRequestParamFields(MeetingRoomRequestParamFields.SEARCH_REQUEST),
                customResponseBodyFields(MeetingRoomResponseBodyFields.MEETING_ROOM_RESPONSE)));
    }

    private static Stream<Arguments> mockMeetingRoomTestPathVariables() {
        return Stream.of(
            Arguments.of(1)
        );
    }


}
