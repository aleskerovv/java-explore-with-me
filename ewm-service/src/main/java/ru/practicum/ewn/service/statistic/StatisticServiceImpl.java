package ru.practicum.ewn.service.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.HitCriteria;
import ru.practicum.ewm.dto.ViewStatisticsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {
    private final StatsClient statsClient;

    @Override
    public List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, List<Long> eventIds) {

        LocalDateTime endDate = LocalDateTime.now();
        List<String> uris = eventIds.stream()
                .map(eventId -> "/events/" + eventId)
                .collect(Collectors.toList());

        HitCriteria criteria = new HitCriteria();
        criteria.setUris(uris)
                .setStart(startDate)
                .setEnd(endDate)
                .setUnique(false);
        log.info("getting hit statistic with params: {}", criteria);

        return statsClient.get(criteria);
    }

    @Override
    public void sendStatistic(EndpointHitDto endpointHitDto) {
        log.info("sending new statistic info {}", endpointHitDto);
        statsClient.post(endpointHitDto);
    }
}
