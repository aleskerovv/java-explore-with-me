package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.model.Event;
import ru.practicum.ewn.service.repos.EventRepository;
import ru.practicum.ewn.service.utils.EventMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventDto getEventById(Long id) {
        log.info("getting event with id {}", id);

        Optional<Event> response = eventRepository.findById(id);

        if (response.isPresent())
            return eventMapper.toDto(response.get());
        else
            throw new NotFoundException(String.format("event with id %d not found", id));
    }

    public List<EventShortDto> findAllEvents(int from, int size) {
        log.info("getting all events");
        Pageable pageable = PageRequest.of(from, size);

        return eventRepository.findAllWithOptionals(pageable)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
