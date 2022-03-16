package com.m2rs.meetingroomservice.service.meetingroom.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.m2rs.core.commons.exception.NotFoundException;
import com.m2rs.core.commons.model.api.response.ApiResult;
import com.m2rs.meetingroomservice.feign.client.UserServiceClient;
import com.m2rs.meetingroomservice.model.api.company.CompanyResponse;
import com.m2rs.meetingroomservice.model.api.meetingroom.CreateMeetingRoomRequest;
import com.m2rs.meetingroomservice.model.api.meetingroom.MeetingRoomResponse;
import com.m2rs.meetingroomservice.model.entity.MeetingRoom;
import com.m2rs.meetingroomservice.repository.meetingroom.MeetingRoomRepository;
import com.m2rs.meetingroomservice.repository.meetingroom.query.MeetingRoomSearchCondition;
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

    @Override
    public List<MeetingRoomResponse> search(MeetingRoomSearchCondition condition) {

        List<MeetingRoom> result = meetingRoomRepository.search(condition);

        return result.stream()
            .map(item -> MeetingRoomResponse.builder()
                .id(item.getId())
                .comId(item.getComId())
                .name(item.getName())
                .createdDate(item.getCreatedDate())
                .lastModifiedDate(item.getLastModifiedDate())
                .build())
            .collect(Collectors.toList());

    }
}
