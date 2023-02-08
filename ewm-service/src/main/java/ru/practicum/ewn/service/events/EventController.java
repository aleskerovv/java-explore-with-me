package ru.practicum.ewn.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.EventShortDto;
import ru.practicum.ewn.service.events.service.EventService;
import ru.practicum.ewn.service.utils.UserEventFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;

    @GetMapping("{id}")
    public EventDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest,
                                 @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .timestamp(LocalDateTime.now())
                .build();

        return eventService.getEventById(id, endpointHitDto);
    }


    @GetMapping
    public List<EventShortDto> getAllEvents(@Valid @ModelAttribute("userEventFilter") UserEventFilter userEventFilter,
                                            @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                            HttpServletRequest servletRequest,
                                            @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .timestamp(LocalDateTime.now())
                .build();

        return eventService.publicGetEvents(userEventFilter, from, size, endpointHitDto);
    }
}
