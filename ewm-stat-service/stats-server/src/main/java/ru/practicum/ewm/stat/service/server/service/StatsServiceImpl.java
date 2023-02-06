package ru.practicum.ewm.stat.service.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.stat.service.server.model.EndpointHit;
import ru.practicum.ewm.stat.service.server.repository.StatsRepository;
import ru.practicum.ewm.stat.service.server.utils.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final EndpointHitMapper mapper;
    private final StatsRepository repository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EndpointHitResponseDto saveStatistics(EndpointHitDto dto) {
        EndpointHit endpointHit = repository.save(mapper.toEndpointHitEntity(dto));

        EndpointHitResponseDto endpointHitDto = mapper.toResponseDto(endpointHit);

        log.info("created new endpoint hit: {}", endpointHit);

        return endpointHitDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                              List<String> uris, boolean isUnique) {
        log.info("looking for statistics");

        List<StatisticCount> hitList;

        if (uris.isEmpty()) {
            hitList = isUnique ? repository.getAllUniqueHits(startDate, endDate)
                    : repository.getAllHits(startDate, endDate);
        } else {
            hitList = isUnique ? repository.getUniqueHits(startDate, endDate, uris)
                    : repository.getHits(startDate, endDate, uris);
        }
        return mapper.toDtoResponse(hitList);

    }
}
