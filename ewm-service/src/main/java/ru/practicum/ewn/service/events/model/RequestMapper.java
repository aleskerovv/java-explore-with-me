package ru.practicum.ewn.service.events.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewn.service.events.dto.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.events.mapper.EventMapper;
import ru.practicum.ewn.service.events.model.Request;
import ru.practicum.ewn.service.events.model.RequestCount;
import ru.practicum.ewn.service.events.model.RequestCountStub;
import ru.practicum.ewn.service.users.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class,
        EventMapper.class})
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    @Mapping(target = "requestStatus", source = "status")
    ParticipantRequestDtoResponse toDto(Request request);

    RequestCountStub toStub(RequestCount requestCount);
}