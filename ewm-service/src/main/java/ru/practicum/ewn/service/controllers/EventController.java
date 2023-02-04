package ru.practicum.ewn.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.services.EventServiceImpl;
import ru.practicum.ewn.service.utils.UserEventFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventServiceImpl eventService;
    private final StatsClient statsClient;

    @GetMapping("{id}")
    public EventDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest,
                                 @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .build();

//        statsClient.post(endpointHitDto);
        log.info(endpointHitDto.toString());

        return eventService.getEventById(id);
    }


    @GetMapping
    public List<EventShortDto> getAllEvents(@Valid @ModelAttribute("userEventFilter") UserEventFilter userEventFilter,
                                            @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return eventService.findEventsByUsersFilters(userEventFilter, from, size);
    }
}
