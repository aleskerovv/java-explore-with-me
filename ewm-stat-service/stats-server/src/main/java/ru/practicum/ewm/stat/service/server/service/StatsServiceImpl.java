package ru.practicum.ewm.stat.service.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.StatisticCount;
import ru.practicum.ewm.dto.ViewStatisticsDto;
import ru.practicum.ewm.stat.service.server.model.Application;
import ru.practicum.ewm.stat.service.server.model.EndpointHit;
import ru.practicum.ewm.stat.service.server.repository.ApplicationRepository;
import ru.practicum.ewm.stat.service.server.repository.StatsRepository;
import ru.practicum.ewm.stat.service.server.utils.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final EndpointHitMapper mapper;
    private final StatsRepository statsRepository;
    private final ApplicationRepository appRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void saveStatistics(EndpointHitDto dto) {
        EndpointHit endpointHit = mapper.toEndpointHitEntity(dto);

        Application app = appRepository.findByName(endpointHit.getApp().getName())
                .or(() -> Optional.of(appRepository.save(endpointHit.getApp())))
                .orElseThrow(() -> new NoSuchElementException("Unexpected error"));

        endpointHit.getApp().setId(app.getId());

        statsRepository.save(endpointHit);

        log.info("created new endpoint hit: {}", endpointHit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatisticsDto> getStatistics(LocalDateTime startDate, LocalDateTime endDate,
                              List<String> uris, boolean isUnique) {
        log.info("looking for statistics");

        List<StatisticCount> hitList;

        if (uris.isEmpty()) {
            hitList = isUnique ? statsRepository.getAllUniqueHits(startDate, endDate)
                    : statsRepository.getAllHits(startDate, endDate);
        } else {
            hitList = isUnique ? statsRepository.getUniqueHits(startDate, endDate, uris)
                    : statsRepository.getHits(startDate, endDate, uris);
        }
        return mapper.toDtoResponse(hitList);

    }
}
