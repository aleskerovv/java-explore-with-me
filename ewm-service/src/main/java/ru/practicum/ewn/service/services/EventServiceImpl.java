package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.dtos.NewEventDto;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.model.Event;
import ru.practicum.ewn.service.model.Location;
import ru.practicum.ewn.service.model.User;
import ru.practicum.ewn.service.repos.EventRepository;
import ru.practicum.ewn.service.repos.LocationRepository;
import ru.practicum.ewn.service.repos.UserRepository;
import ru.practicum.ewn.service.utils.EventMapper;
import ru.practicum.ewn.service.utils.LocationMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    //TODO: continue with Events on Saturday, write IT and API tests on all the functions

    public EventDto getEventById(Long id) {
        log.info("getting event with id {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d not found", id)));

        return eventMapper.toDto(event);
    }

    public List<EventShortDto> findAllEvents(int from, int size) {
        log.info("getting all events");
        Pageable pageable = PageRequest.of(from, size);

        return eventRepository.findAllWithOptionals(pageable)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    //TODO: research for category name return when creates
    public EventDto createEvent(NewEventDto eventDto, Long initiatorId) {
        log.info("creating new event");
        User user = userRepository.findById(initiatorId)
                .orElseThrow(() -> new NotFoundException(String.format("user with id %d not found", initiatorId)));

        Location location = locationRepository.save(locationMapper.toEntity(eventDto.getLocation()));

        Event event = eventMapper.toEntity(eventDto);

        event.setInitiator(user)
                .setCreatedOn(LocalDateTime.now())
                .setEventDate(eventDto.getEventDate())
                .setLocation(location);

        if (event.getRequestModeration()) {
            event.setEventState(EventState.PENDING);
        } else {
            event.setEventState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }

        return eventMapper.toDto(eventRepository.save(event));
    }

    public List<EventShortDto> getEventsByInitiatorId(Long initiatorId, Pageable pageable) {
        return eventRepository.findAllByInitiatorId(initiatorId, pageable)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
