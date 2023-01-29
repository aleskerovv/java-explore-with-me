package ru.practicum.ewm.stat.service.server.service;

import ru.practicum.ewm.stat.service.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stat.service.dto.HitStats;
import ru.practicum.ewm.stat.service.dto.StatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stat.service.server.model.EndpointHit;
import ru.practicum.ewm.stat.service.server.repository.StatsRepository;
import ru.practicum.ewm.stat.service.server.utils.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final EndpointHitMapper mapper;

    private final StatsRepository repository;

    @Override
    public EndpointHitResponseDto saveStatistics(EndpointHitDto dto) {
        EndpointHit endpointHit = repository.save(mapper.toEndpointHitEntity(dto));

        EndpointHitResponseDto endpointHitDto = mapper.toResponseDto(endpointHit);

        log.info("created new endpoint hit: {}", endpointHit);

        return endpointHitDto;
    }

    @Override
    public StatResponse getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                                      Collection<String> uris, boolean isUnique) {
        log.info("looking for statistics");
        List<HitStats> hitList = isUnique ? repository.getUniqueHits(startDate, endDate, uris)
                : repository.getHits(startDate, endDate, uris);

        return StatResponse.builder().stats(hitList).build();

    }
}
