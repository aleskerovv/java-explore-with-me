package ru.practicum.ewm.stat.service.server.service;

import ru.practicum.ewm.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    EndpointHitResponseDto saveStatistics(EndpointHitDto dto);

    List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                       List<String> uris, boolean isUnique);
}
