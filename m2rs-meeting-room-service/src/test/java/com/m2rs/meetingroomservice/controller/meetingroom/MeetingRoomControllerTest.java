package com.m2rs.meetingroomservice.controller.meetingroom;

import static com.m2rs.core.document.utils.SnippetUtils.customRequestBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.customResponseBodyFields;
import static com.m2rs.core.document.utils.SnippetUtils.getDefaultHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.meetingroomservice.commons.field.meetingroom.MeetingRoomRequestBodyFields;
import com.m2rs.meetingroomservice.commons.field.meetingroom.MeetingRoomResponseBodyFields;
import com.m2rs.meetingroomservice.controller.CommonControllerTest;
import com.m2rs.meetingroomservice.feign.client.UserServiceClient;
import com.m2rs.meetingroomservice.model.api.company.CompanyResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

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
        MeetingRoom mockMeetingRoom =
            MeetingRoom.builder()
                .id(1L)
                .comId(1L)
                .name("test-meeting-room")
                .build();

        /*
         mock user service client
         */
        when(userServiceClient.getCompany(any())).thenReturn(ApiResult.ok(mockCompanyResponse));

        /*
         mock meeting room repository
         */
        when(meetingRoomRepository.save(any())).thenReturn(mockMeetingRoom);

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


}
