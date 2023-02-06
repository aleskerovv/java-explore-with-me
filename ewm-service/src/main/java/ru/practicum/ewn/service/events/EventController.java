package ru.practicum.ewn.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.HitCriteria;
import ru.practicum.ewm.dto.ViewStatisticsDto;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.EventShortDto;
import ru.practicum.ewn.service.events.service.EventServiceImpl;
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
    private final EventServiceImpl eventService;
    private final StatsClient statsClient;


    @GetMapping("{id}")
    public EventDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest,
                                 @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .timestamp(LocalDateTime.now())
                .build();

        statsClient.post(endpointHitDto);

        EventDto dto = eventService.getEventById(id);

        HitCriteria criteria = new HitCriteria();
        criteria.setUris(List.of("/events/" + id))
                .setStart(LocalDateTime.now().minusMinutes(1))
                .setEnd(LocalDateTime.now().plusMinutes(5))
                .setUnique(true);

        List<ViewStatisticsDto> list = statsClient.get(criteria);

        dto.setViews(list.get(0).getHits().intValue());

        return dto;
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

        log.info(endpointHitDto.toString());
        statsClient.post(endpointHitDto);

        return eventService.publicGetEvents(userEventFilter, from, size);
    }
}
