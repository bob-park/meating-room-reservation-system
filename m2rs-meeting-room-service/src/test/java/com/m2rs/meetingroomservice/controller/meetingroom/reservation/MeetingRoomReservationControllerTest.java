package com.m2rs.meetingroomservice.controller.meetingroom.reservation;

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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.meetingroomservice.commons.field.meetingroom.reservation.MeetingRoomReservationRequestBodyFields;
import com.m2rs.meetingroomservice.commons.field.meetingroom.reservation.MeetingRoomReservationRequestParamFields;
import com.m2rs.meetingroomservice.commons.field.meetingroom.reservation.MeetingRoomReservationResponseBodyFields;
import com.m2rs.meetingroomservice.controller.CommonControllerTest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.MeetingRoomReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;

class MeetingRoomReservationControllerTest extends CommonControllerTest {

    private static final String RESERVATION_API = "/meeting/room/{mrId}/reservation";

    @MockBean
    MeetingRoomRepository meetingRoomRepository;

    @MockBean
    MeetingRoomReservationRepository meetingRoomReservationRepository;

    @BeforeEach
    void setup() {

        // mock meeting room
        MeetingRoom mockMeetingRoom =
            MeetingRoom.builder()
                .id(1L)
                .comId(1L)
                .name("test meeting room")
                .build();

        // mock meeting room reservation
        MeetingRoomReservation mockReservation =
            MeetingRoomReservation.builder()
                .id(1L)
                .title("test meeting room reservation")
                .startDate(LocalDate.now().atStartOfDay())
                .endDate(LocalDate.now().atTime(LocalTime.MAX))
                .build();

        mockReservation.setUserId(1L);

        mockReservation.setMeetingRoom(mockMeetingRoom);

        /*
         mock meeting room repository
         */
        when(meetingRoomRepository.findById(any())).thenReturn(Optional.of(mockMeetingRoom));

        /*
         mock meeting room reservation repository
         */
        when(meetingRoomReservationRepository.save(any())).thenReturn(mockReservation);
        when(meetingRoomReservationRepository.search(any()))
            .thenReturn(Collections.singletonList(mockReservation));
        when(meetingRoomReservationRepository.findById(any()))
            .thenReturn(Optional.of(mockReservation));
        when(meetingRoomReservationRepository.checkAvailableReservation(any(), any(), any(), any()))
            .thenReturn(true);
    }

    @ParameterizedTest
    @ValueSource(longs = 1)
    @DisplayName("create meeting room reservation test")
    void createMeetingRoomReservationTest(long mrId) throws Exception {

        CreateMeetingRoomReservationRequest createRequest =
            CreateMeetingRoomReservationRequest.builder()
                .title("test meeting room reservation")
                .startDate(LocalDate.now().atStartOfDay())
                .endDate(LocalDate.now().atTime(LocalTime.MAX))
                .build();

        mockMvc.perform(post(RESERVATION_API, mrId)
                .headers(getDefaultHeaders())
                .content(toJson(createRequest)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(MeetingRoomReservationRequestBodyFields.CREATE_RESERVATION),
                customResponseBodyFields(
                    MeetingRoomReservationResponseBodyFields.RESERVATION_RESPONSE)));

    }

    @ParameterizedTest
    @MethodSource("mockReservationTestPathVariables")
    @DisplayName("modify meeting room reservation test")
    void modifyMeetingRoomReservationTest(long mrId, long mrrId) throws Exception {

        ModifyMeetingRoomReservationRequest modifyRequest =
            ModifyMeetingRoomReservationRequest.builder()
                .startDate(LocalDate.now().plusDays(1).atStartOfDay())
                .endDate(LocalDate.now().atTime(LocalTime.MAX))
                .build();

        mockMvc.perform(put(RESERVATION_API + "/{mrrId}", mrId, mrrId)
                .headers(getDefaultHeaders())
                .content(toJson(modifyRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                        .description("회의실 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER)),
                    parameterWithName("mrrId")
                        .description("회의실 예약 아이디")
                        .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestBodyFields(MeetingRoomReservationRequestBodyFields.MODIFY_RESERVATION),
                customResponseBodyFields(
                    MeetingRoomReservationResponseBodyFields.RESERVATION_RESPONSE)
            ));
    }

    @ParameterizedTest
    @MethodSource("mockReservationTestPathVariables")
    @DisplayName("remove meeting room reservation test")
    void removeMeetingRoomReservationTest(long mrId, long mrrId) throws Exception {

        mockMvc.perform(delete(RESERVATION_API + "/{mrrId}", mrId, mrrId)
                .headers(getDefaultHeaders()))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andDo(document.document(customPathParamFields(
                parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER)),
                parameterWithName("mrrId")
                    .description("회의실 예약 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER)))));

    }

    @ParameterizedTest
    @ValueSource(longs = 1)
    @DisplayName("get meeting room reservation list test ")
    void getMeetingRoomReservationListTest(long mrId) throws Exception {

        mockMvc.perform(get(RESERVATION_API + "/list", mrId)
                .headers(getDefaultHeaders())
                .param("userId", "1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                customPathParamFields(parameterWithName("mrId")
                    .description("회의실 아이디")
                    .attributes(generateType(JsonFieldType.NUMBER))),
                customRequestParamFields(MeetingRoomReservationRequestParamFields.SEARCH_REQUEST),
                customResponseBodyFields(
                    MeetingRoomReservationResponseBodyFields.RESERVATION_RESPONSE)
            ));

    }


    private static Stream<Arguments> mockReservationTestPathVariables() {
        return Stream.of(
            Arguments.of(1, 1)
        );
    }

}
