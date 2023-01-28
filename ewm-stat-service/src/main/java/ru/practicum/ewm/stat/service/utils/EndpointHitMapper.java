package ru.practicum.ewm.stat.service.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.stat.service.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stat.service.model.EndpointHit;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    @Mapping(target = "hitDate", source = "timestamp")
    EndpointHit toEndpointHitEntity(EndpointHitDto endpointHitDto);

    @Mapping(target = "timestamp", source = "hitDate")
    EndpointHitResponseDto toResponseDto(EndpointHit endpointHit);
}
