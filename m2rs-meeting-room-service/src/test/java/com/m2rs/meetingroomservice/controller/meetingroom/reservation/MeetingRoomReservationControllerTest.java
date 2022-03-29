package com.m2rs.meetingroomservice.controller.meetingroom.reservation;

import com.m2rs.meetingroomservice.controller.CommonControllerTest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.MeetingRoomReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;

class MeetingRoomReservationControllerTest extends CommonControllerTest {

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
                .build();

        /*
         mock meeting room repository
         */

        /*
         mock meeting room reservation repository
         */

    }

}
