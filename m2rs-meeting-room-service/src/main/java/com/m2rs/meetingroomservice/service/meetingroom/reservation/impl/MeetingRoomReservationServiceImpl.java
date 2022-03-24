package com.m2rs.meetingroomservice.service.meetingroom.reservation.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RestPrincipal;
import com.m2rs.meetingroomservice.authentication.context.RestAuthenticationContextHolder;
import com.m2rs.meetingroomservice.exception.AlreadyReservationException;
import com.m2rs.meetingroomservice.exception.ReservationUserDifferentException;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.MeetingRoomReservationRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.query.MeetingRoomReservationSearchCondition;
import com.m2rs.meetingroomservice.service.meetingroom.reservation.MeetingRoomReservationService;
import java.util.List;
import java.util.Objects;
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
    public MeetingRoomReservationResponse createReservation(Id<MeetingRoom, Long> mrId,
        CreateMeetingRoomReservationRequest reservationRequest) {

        checkNotNull(mrId, "mrId must be provided.");
        checkNotNull(reservationRequest.getStartDate(), "startDate must be provided.");
        checkNotNull(reservationRequest.getEndDate(), "endDate must be provided.");

        MeetingRoom meetingRoom = meetingRoomRepository.findById(mrId.value())
            .orElseThrow(() ->
                new NotFoundException(MeetingRoom.class, mrId.value()));

        boolean availableReservation = meetingRoomReservationRepository
            .checkAvailableReservation(mrId,
                null,
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

    @Transactional
    @Override
    public MeetingRoomReservationResponse modifyReservation(Id<MeetingRoom, Long> mrId,
        Id<MeetingRoomReservation, Long> mrrId, ModifyMeetingRoomReservationRequest modifyRequest) {

        checkNotNull(mrId, "mrId must be provided.");
        checkNotNull(mrrId, "mrrId must be provided.");

        meetingRoomRepository.findById(mrId.value())
            .orElseThrow(() -> new NotFoundException(MeetingRoom.class, mrId.value()));

        boolean availableReservation =
            meetingRoomReservationRepository.checkAvailableReservation(mrId,
                mrrId,
                modifyRequest.getStartDate(),
                modifyRequest.getEndDate());

        if (!availableReservation) {
            throw new AlreadyReservationException(modifyRequest.getStartDate(),
                modifyRequest.getEndDate());
        }

        MeetingRoomReservation meetingRoomReservation =
            meetingRoomReservationRepository.findById(mrrId.value())
                .orElseThrow(() ->
                    new NotFoundException(MeetingRoomReservation.class, mrrId.value()));

        RestPrincipal principal = RestAuthenticationContextHolder.getContextHolder().getPrincipal();

        if (!Objects.equals(principal.getId(), meetingRoomReservation.getUserId())) {
            throw new ReservationUserDifferentException();
        }

        meetingRoomReservation.modify(modifyRequest);

        return MeetingRoomReservationResponse.builder()
            .id(meetingRoomReservation.getId())
            .userId(meetingRoomReservation.getUserId())
            .mrId(meetingRoomReservation.getMeetingRoom().getId())
            .startDate(meetingRoomReservation.getStartDate())
            .endDate(meetingRoomReservation.getEndDate())
            .createdDate(meetingRoomReservation.getCreatedDate())
            .lastModifiedDate(meetingRoomReservation.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public MeetingRoomReservationResponse removeReservation(
        Id<MeetingRoomReservation, Long> mrrId) {

        checkNotNull(mrrId, "mrrId must be provided");

        MeetingRoomReservation meetingRoomReservation = meetingRoomReservationRepository.findById(
                mrrId.value())
            .orElseThrow(() -> new NotFoundException(MeetingRoomReservation.class, mrrId.value()));

        RestPrincipal principal = RestAuthenticationContextHolder.getContextHolder().getPrincipal();

        if (!Objects.equals(principal.getId(), meetingRoomReservation.getUserId())) {
            throw new ReservationUserDifferentException();
        }

        meetingRoomReservationRepository.delete(meetingRoomReservation);

        return MeetingRoomReservationResponse.builder()
            .id(meetingRoomReservation.getId())
            .build();
    }
}
