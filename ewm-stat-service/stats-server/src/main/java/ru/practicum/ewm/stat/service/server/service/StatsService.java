package ru.practicum.ewm.stat.service.server.service;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatisticsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void saveStatistics(EndpointHitDto dto);

    List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                                          List<String> uris, boolean isUnique);
}
