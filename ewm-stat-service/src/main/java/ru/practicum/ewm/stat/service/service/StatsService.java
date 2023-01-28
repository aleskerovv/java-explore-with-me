package ru.practicum.ewm.stat.service.service;

import ru.practicum.ewm.stat.service.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stat.service.dto.StatResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public interface StatsService {

    EndpointHitResponseDto saveStatistics(EndpointHitDto dto);

    StatResponse getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                               Collection<String> uris, boolean isUnique);
}
