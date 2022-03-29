package com.m2rs.meetingroomservice.service.meetingroom.reservation.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.model.Id;
import com.m2rs.core.security.model.RestPrincipal;
import com.m2rs.meetingroomservice.authentication.context.RestAuthenticationContextHolder;
import com.m2rs.meetingroomservice.exception.AlreadyReservationException;
import com.m2rs.meetingroomservice.exception.ReservationUserDifferentException;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.CreateMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.MeetingRoomReservationResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.ModifyMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.reservation.SearchMeetingRoomReservationRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.model.entity.MeetingRoomReservation;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.reservation.MeetingRoomReservationRepository;
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

        checkArgument(isNotEmpty(mrId), "mrId must be provided.");
        checkArgument(isNotEmpty(reservationRequest.getTitle()), "title must be provided.");
        checkArgument(isNotEmpty(reservationRequest.getStartDate()), "startDate must be provided.");
        checkArgument(isNotEmpty(reservationRequest.getEndDate()), "endDate must be provided.");

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

        MeetingRoomReservation reservation = MeetingRoomReservation.builder()
            .title(reservationRequest.getTitle())
            .description(reservationRequest.getDescription())
            .numberOfUsers(reservationRequest.getNumberOfUsers())
            .startDate(reservationRequest.getStartDate())
            .endDate(reservationRequest.getEndDate())
            .build();

        reservation.setMeetingRoom(meetingRoom);

        MeetingRoomReservation savedReservation =
            meetingRoomReservationRepository.save(reservation);

        return toResponse(savedReservation);
    }

    @Override
    public List<MeetingRoomReservationResponse> searchReservation(Id<MeetingRoom, Long> mrId,
        SearchMeetingRoomReservationRequest searchRequest) {

        checkArgument(isNotEmpty(mrId), "mrId must be provided.");

        List<MeetingRoomReservation> result =
            meetingRoomReservationRepository.search(searchRequest.getQueryCondition(mrId));

        return result.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MeetingRoomReservationResponse modifyReservation(Id<MeetingRoom, Long> mrId,
        Id<MeetingRoomReservation, Long> mrrId, ModifyMeetingRoomReservationRequest modifyRequest) {

        checkArgument(isNotEmpty(mrId), "mrId must be provided.");
        checkArgument(isNotEmpty(mrrId), "mrrId must be provided.");

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

        return toResponse(meetingRoomReservation);
    }

    @Transactional
    @Override
    public MeetingRoomReservationResponse removeReservation(
        Id<MeetingRoomReservation, Long> mrrId) {

        checkArgument(isNotEmpty(mrrId), "mrrId must be provided");

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

    private MeetingRoomReservationResponse toResponse(MeetingRoomReservation entity) {
        return MeetingRoomReservationResponse.builder()
            .id(entity.getId())
            .userId(entity.getUserId())
            .mrId(entity.getMeetingRoom().getId())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .numberOfUsers(entity.getNumberOfUsers())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .createdDate(entity.getCreatedDate())
            .lastModifiedDate(entity.getLastModifiedDate())
            .build();
    }
}
