package ru.practicum.ewn.service.statistic;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatisticsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, List<Long> eventIds);

    void sendStatistic(EndpointHitDto endpointHitDto);
}
