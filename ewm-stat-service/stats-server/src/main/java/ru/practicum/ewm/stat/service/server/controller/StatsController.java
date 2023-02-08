package ru.practicum.ewm.stat.service.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.stat.service.server.service.StatsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitResponseDto addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        return statsService.saveStatistics(endpointHitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatisticsDto> getStatistics(@Valid @ModelAttribute(name = "hitCriteria") HitCriteria hitCriteria) {
        log.info("received new request with hit criteria: {}", hitCriteria);

        return statsService.getStatistics(hitCriteria.getStart(),
                hitCriteria.getEnd(),
                Optional.ofNullable(hitCriteria.getUris()).orElse(List.of()),
                hitCriteria.isUnique());
    }
}
