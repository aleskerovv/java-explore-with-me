package ru.practicum.ewn.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.services.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventServiceImpl eventServiceImpl;
    private final StatsClient statsClient;

    @GetMapping("{id}")
    public EventDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest) {
        String appName = "ewm-main-service";
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .build();

//        statsClient.post(endpointHitDto);
        log.info(endpointHitDto.toString());

        return eventServiceImpl.getEventById(id);
    }

    @GetMapping
    public List<EventShortDto> getAllEvents(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return eventServiceImpl.findAllEvents(from, size);
    }
}
