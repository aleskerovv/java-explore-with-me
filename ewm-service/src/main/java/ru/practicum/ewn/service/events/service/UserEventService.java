package ru.practicum.ewn.service.events.service;

import ru.practicum.ewn.service.events.dto.*;

import java.util.List;

public interface UserEventService {
    List<EventShortDto> getEventsByUserId(Long userId, int from, int size);

    EventDto createEventByUser(Long userId, NewEventDto eventDto);

    EventDto getEventById(Long userId, Long eventId);

    EventDto updateUsersEventById(Long userId, Long eventId, UserEventUpdateDto eventDto);

    List<ParticipantRequestDtoResponse> getEventsRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest request);
}
