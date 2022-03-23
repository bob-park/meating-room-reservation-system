package com.m2rs.meetingroomservice.service.meetingroom.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.exception.task.AlreadyExecuteException;
import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.core.model.Id;
import com.m2rs.meetingroomservice.feign.client.UserServiceClient;
import com.m2rs.meetingroomservice.model.api.company.CompanyResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.ModifyMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.SearchMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.query.SearchMeetingRoomQueryCondition;
import com.m2rs.meetingroomservice.service.meetingroom.MeetingRoomService;
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
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeetingRoomRepository meetingRoomRepository;

    private final UserServiceClient userServiceClient;

    @Override
    public List<MeetingRoomResponse> search(SearchMeetingRoomRequest searchRequest) {

        List<MeetingRoom> result = meetingRoomRepository.search(searchRequest.getQueryCondition());

        return result.stream()
            .map(item -> MeetingRoomResponse.builder()
                .id(item.getId())
                .comId(item.getComId())
                .name(item.getName())
                .isActive(item.getIsActive())
                .createdDate(item.getCreatedDate())
                .lastModifiedDate(item.getLastModifiedDate())
                .build())
            .collect(Collectors.toList());

    }

    @Override
    public MeetingRoomResponse find(Id<MeetingRoom, Long> id) {

        checkNotNull(id, "id must be provided.");

        MeetingRoom meetingRoom = meetingRoomRepository.findById(id.value())
            .orElseThrow(() -> new NotFoundException(MeetingRoom.class, id.value()));

        return MeetingRoomResponse.builder()
            .id(meetingRoom.getId())
            .comId(meetingRoom.getComId())
            .name(meetingRoom.getName())
            .isActive(meetingRoom.getIsActive())
            .createdDate(meetingRoom.getCreatedDate())
            .lastModifiedDate(meetingRoom.getLastModifiedDate())
            .build();

    }

    @Transactional
    @Override
    public MeetingRoomResponse create(CreateMeetingRoomRequest createRequest) {

        checkArgument(isNotEmpty(createRequest.getComId()), "comId must be provided.");
        checkArgument(isNotEmpty(createRequest.getName()), "name must be provided.");

        ApiResult<CompanyResponse> companyResponse =
            userServiceClient.getCompany(createRequest.getComId());

        log.debug("company={}", companyResponse.getResult());

        if (isEmpty(companyResponse.getResult())) {
            throw new NotFoundException(CompanyResponse.class, createRequest.getComId());
        }

        MeetingRoom savedMeetingRoom = meetingRoomRepository.save(MeetingRoom.builder()
            .comId(createRequest.getComId())
            .name(createRequest.getName())
            .build());

        return MeetingRoomResponse.builder()
            .id(savedMeetingRoom.getId())
            .comId(savedMeetingRoom.getComId())
            .name(savedMeetingRoom.getName())
            .isActive(savedMeetingRoom.getIsActive())
            .createdDate(savedMeetingRoom.getCreatedDate())
            .lastModifiedDate(savedMeetingRoom.getLastModifiedDate())
            .build();
    }

    @Transactional
    @Override
    public MeetingRoomResponse modify(Id<MeetingRoom, Long> id,
        ModifyMeetingRoomRequest modifyRequest) {

        checkNotNull(id, "id must be provided.");

        MeetingRoom meetingRoom = meetingRoomRepository.findById(id.value())
            .orElseThrow(() -> new NotFoundException(MeetingRoom.class, id.value()));

        meetingRoom.modifyName(modifyRequest.getName());

        return MeetingRoomResponse.builder()
            .id(meetingRoom.getId())
            .comId(meetingRoom.getComId())
            .name(meetingRoom.getName())
            .isActive(meetingRoom.getIsActive())
            .createdDate(meetingRoom.getCreatedDate())
            .lastModifiedDate(meetingRoom.getLastModifiedDate())
            .build();
    }


    @Transactional
    @Override
    public MeetingRoomResponse changeActive(Id<MeetingRoom, Long> id, boolean isActive) {

        checkNotNull(id, "id must be provided.");

        MeetingRoom meetingRoom = meetingRoomRepository.findById(id.value())
            .orElseThrow(() -> new NotFoundException(MeetingRoom.class, id.value()));

        if (Boolean.TRUE.equals(meetingRoom.getIsActive()) == isActive) {
            throw new AlreadyExecuteException();
        }

        meetingRoom.setIsActive(isActive);

        return MeetingRoomResponse.builder()
            .id(meetingRoom.getId())
            .comId(meetingRoom.getComId())
            .name(meetingRoom.getName())
            .isActive(meetingRoom.getIsActive())
            .createdDate(meetingRoom.getCreatedDate())
            .lastModifiedDate(meetingRoom.getLastModifiedDate())
            .build();
    }
}
