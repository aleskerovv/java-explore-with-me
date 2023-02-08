package ru.practicum.ewn.service.events.service;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.EventShortDto;
import ru.practicum.ewn.service.utils.UserEventFilter;

import java.util.List;

public interface EventService {

    EventDto getEventById(Long eventId, EndpointHitDto endpointHitDto);

    List<EventShortDto> publicGetEvents(UserEventFilter filter, int from, int size, EndpointHitDto endpointHitDto);
}
