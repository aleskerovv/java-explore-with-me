package ru.practicum.ewm.stat.service.server.service;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.StatResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    EndpointHitResponseDto saveStatistics(EndpointHitDto dto);

    StatResponse getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                               List<String> uris, boolean isUnique);
}
