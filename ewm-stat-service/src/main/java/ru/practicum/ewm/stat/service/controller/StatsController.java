package ru.practicum.ewm.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stat.service.dto.EndpointHitDto;
import ru.practicum.ewm.stat.service.dto.EndpointHitResponseDto;
import ru.practicum.ewm.stat.service.dto.HitCriteria;
import ru.practicum.ewm.stat.service.dto.StatResponse;
import ru.practicum.ewm.stat.service.service.StatsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    StatResponse getStatistics(@Valid HitCriteria hitCriteria) {
        log.info("received new request with hit criteria: {}", hitCriteria);
        return statsService.getStatistics(hitCriteria.getStart(),
                hitCriteria.getEnd(),
                Optional.ofNullable(hitCriteria.getUris()).orElse(List.of("")),
                hitCriteria.isUnique());
    }

    @PostMapping("/hit")
    EndpointHitResponseDto addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        return statsService.saveStatistics(endpointHitDto);
    }

}
