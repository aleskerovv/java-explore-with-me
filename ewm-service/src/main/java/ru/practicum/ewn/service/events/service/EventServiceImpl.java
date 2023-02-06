package ru.practicum.ewn.service.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.events.dao.EventRepository;
import ru.practicum.ewn.service.events.dao.RequestRepository;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.EventShortDto;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.utils.EventMapper;
import ru.practicum.ewn.service.utils.RequestMapper;
import ru.practicum.ewn.service.utils.UserEventFilter;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewn.service.utils.UserEventSpecification.filtersFromUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> publicGetEvents(UserEventFilter filter, int from, int size) {
        log.info("getting all events by users");

        Pageable pageable = PageRequest.of(from, size);

        Specification<Event> specification = filtersFromUser(filter);

        return eventRepository.findAll(specification, pageable).stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventById(Long id) {
        log.info("getting event with id {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d not found", id)));

        return eventMapper.toDto(event);
    }
}