package ru.practicum.ewm.stat.service.server.utils;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.practicum.ewm.stat.service.server.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    @Mapping(target = "hitDate", source = "timestamp")
    EndpointHit toEndpointHitEntity(EndpointHitDto endpointHitDto);

    @Mapping(target = "timestamp", source = "hitDate")
    EndpointHitResponseDto toResponseDto(EndpointHit endpointHit);
}
