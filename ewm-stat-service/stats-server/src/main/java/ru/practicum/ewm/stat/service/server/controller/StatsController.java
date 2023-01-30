package ru.practicum.ewm.stat.service.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.HitCriteria;
import ru.practicum.ewm.dto.StatResponse;
import ru.practicum.ewm.stat.service.server.service.StatsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/stats")
    public StatResponse getStatistics(@Valid @ModelAttribute(name = "hitCriteria") HitCriteria hitCriteria) {
        log.info("received new request with hit criteria: {}", hitCriteria);
        return statsService.getStatistics(hitCriteria.getStart(),
                hitCriteria.getEnd(),
                Optional.ofNullable(hitCriteria.getUris()).orElse(List.of()),
                hitCriteria.isUnique());
    }

    @PostMapping("/hit")
    public EndpointHitResponseDto addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        return statsService.saveStatistics(endpointHitDto);
    }

}
