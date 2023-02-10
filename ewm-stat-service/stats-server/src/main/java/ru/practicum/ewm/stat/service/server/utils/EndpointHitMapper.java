package ru.practicum.ewm.stat.service.server.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.StatisticCount;
import ru.practicum.ewm.dto.ViewStatisticsDto;
import ru.practicum.ewm.stat.service.server.model.EndpointHit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
    @Mapping(target = "hitDate", source = "timestamp")
    @Mapping(target = "app.name", source = "app")
    EndpointHit toEndpointHitEntity(EndpointHitDto endpointHitDto);

    List<ViewStatisticsDto> toDtoResponse(List<StatisticCount> statistic);
}
