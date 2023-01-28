package ru.practicum.ewm.stat.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stat.service.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stat.service.dto.HitStats;
import ru.practicum.ewm.stat.service.dto.StatResponse;
import ru.practicum.ewm.stat.service.model.EndpointHit;
import ru.practicum.ewm.stat.service.repository.StatsRepository;
import ru.practicum.ewm.stat.service.utils.EndpointHitMapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        List<HitStats> hitsList = isUnique ? repository.getUniqueHits(startDate, endDate, uris)
                : repository.getHits(startDate, endDate, uris);

        return StatResponse.builder().stats(hitsList).build();

    }

//    private StatResponse getStatsByUnique(List<EndpointHit> list, boolean isUnique) {
//        if (isUnique) {
//            Map<Pair<String, String>, Set<String>> uniqueHits = new HashMap<>();
//
//            list.forEach(hit -> {
//                Pair<String, String> key = Pair.of(hit.getApp(), hit.getUri());
//
//                if (!uniqueHits.containsKey(key)) {
//                    uniqueHits.put(key, new HashSet<>());
//                }
//
//                uniqueHits.get(key).add(hit.getIp());
//            });
//
//            return StatResponse.builder()
//                    .stats(
//                            uniqueHits.entrySet()
//                                    .stream()
//                                    .map(entry -> HitStats.builder()
//                                            .app(entry.getKey().getFirst())
//                                            .uri(entry.getKey().getSecond())
//                                            .hits(entry.getValue().size())
//                                            .build())
//                                    .sorted(Comparator.comparingInt(HitStats::getHits).reversed())
//                                    .collect(Collectors.toList())
//                    )
//                    .build();
//        } else {
//            Map<Pair<String, String>, Integer> hits = new HashMap<>();
//
//            list.forEach(hit -> {
//                Pair<String, String> key = Pair.of(hit.getApp(), hit.getUri());
//                if (!hits.containsKey(key)) {
//                    hits.put(key, 1);
//                } else {
//                    hits.put(key, hits.get(key) + 1);
//                }
//            });
//
//            return StatResponse.builder()
//                    .stats(
//                            hits.entrySet()
//                                    .stream()
//                                    .map(entry -> HitStats.builder()
//                                            .app(entry.getKey().getFirst())
//                                            .uri(entry.getKey().getSecond())
//                                            .hits(entry.getValue())
//                                            .build())
//                                    .sorted(Comparator.comparingInt(HitStats::getHits).reversed())
//                                    .collect(Collectors.toList())
//                    )
//                    .build();
//        }
//    }
}
