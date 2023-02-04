package ru.practicum.ewn.service.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewn.service.dtos.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.model.Request;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class,
        EventMapper.class})
public interface RequestMapper {
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    @Mapping(target = "requestStatus", source = "status")
    ParticipantRequestDtoResponse toDto(Request request);
}