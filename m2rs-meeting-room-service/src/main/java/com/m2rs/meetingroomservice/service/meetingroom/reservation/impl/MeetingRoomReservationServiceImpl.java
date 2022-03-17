package com.m2rs.meetingroomservice.service.meetingroom.reservation.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.exception.AlreadyReservationException;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.MeetingRoomReservationRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationSearchCondition;
import com.m2rs.meetingroomservice.service.meetingroom.reservation.MeetingRoomReservationService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingRoomReservationServiceImpl implements MeetingRoomReservationService {

    private final MeetingRoomRepository meetingRoomRepository;
    private final MeetingRoomReservationRepository meetingRoomReservationRepository;

    @Transactional
    @Override
    public MeetingRoomReservationResponse createReservation(
        CreateMeetingRoomReservationRequest reservationRequest) {

        checkArgument(isNotEmpty(reservationRequest.getMrId()), "mrId must be provided.");
        checkArgument(isNotEmpty(reservationRequest.getStartDate()), "startDate must be provided.");
        checkArgument(isNotEmpty(reservationRequest.getEndDate()), "endDate must be provided.");

        MeetingRoom meetingRoom = meetingRoomRepository.findById(reservationRequest.getMrId())
            .orElseThrow(() ->
                new NotFoundException(MeetingRoom.class, reservationRequest.getMrId()));

        boolean availableReservation = meetingRoomReservationRepository
            .checkAvailableReservation(Id.of(MeetingRoom.class, reservationRequest.getMrId()),
                reservationRequest.getStartDate(),
                reservationRequest.getEndDate());

        if (!availableReservation) {
            throw new AlreadyReservationException(reservationRequest.getStartDate(),
                reservationRequest.getEndDate());
        }

        MeetingRoomReservation savedReservation =
            meetingRoomReservationRepository.save(MeetingRoomReservation.builder()
                .meetingRoom(meetingRoom)
                .startDate(reservationRequest.getStartDate())
                .endDate(reservationRequest.getEndDate())
                .build());

        return MeetingRoomReservationResponse.builder()
            .id(savedReservation.getId())
            .userId(savedReservation.getUserId())
            .mrId(savedReservation.getMeetingRoom().getId())
            .startDate(savedReservation.getStartDate())
            .endDate(savedReservation.getEndDate())
            .createdDate(savedReservation.getCreatedDate())
            .lastModifiedDate(savedReservation.getLastModifiedDate())
            .build();
    }

    @Override
    public List<MeetingRoomReservationResponse> searchReservation(
        MeetingRoomReservationSearchCondition condition) {

        List<MeetingRoomReservation> result = meetingRoomReservationRepository.search(condition);

        return result.stream()
            .map(item -> MeetingRoomReservationResponse.builder()
                .id(item.getId())
                .mrId(item.getMeetingRoom().getId())
                .userId(item.getUserId())
                .startDate(item.getStartDate())
                .endDate(item.getEndDate())
                .createdDate(item.getCreatedDate())
                .lastModifiedDate(item.getLastModifiedDate())
                .build())
            .collect(Collectors.toList());
    }
}
