package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.dtos.NewEventDto;
import ru.practicum.ewn.service.dtos.UpdateEventAdminRequest;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.StateAction;
import ru.practicum.ewn.service.handlers.DataValidationException;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.model.Category;
import ru.practicum.ewn.service.model.Event;
import ru.practicum.ewn.service.model.User;
import ru.practicum.ewn.service.repos.CategoryRepository;
import ru.practicum.ewn.service.repos.EventRepository;
import ru.practicum.ewn.service.repos.UserRepository;
import ru.practicum.ewn.service.utils.AdminEventFilter;
import ru.practicum.ewn.service.utils.EventMapper;
import ru.practicum.ewn.service.utils.UserEventFilter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewn.service.utils.AdminEventSpecification.getAdminsFilters;
import static ru.practicum.ewn.service.utils.UserEventSpecification.filtersFromUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //TODO: continue with Events on Saturday, write IT and API tests on all the functions

    public EventDto getEventById(Long id) {
        log.info("getting event with id {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d not found", id)));

        return eventMapper.toDto(event);
    }

    public List<EventDto> findEventsByAdminsFilters(AdminEventFilter filter, int from, int size) {
        log.info("getting all events by admin");
        Pageable pageable = PageRequest.of(from, size);

        Specification<Event> specification = getAdminsFilters(filter);

        List<EventDto> events = eventRepository.findAll(specification, pageable).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());

        return events.stream()
                .sorted(Comparator.comparing(EventDto::getViews).reversed())
                .collect(Collectors.toList());
    }

    public List<EventShortDto> findEventsByUsersFilters(UserEventFilter filter, int from, int size) {
        log.info("getting all events by users");
        Pageable pageable = PageRequest.of(from, size);

        Specification<Event> specification = filtersFromUser(filter);

        List<Event> events = eventRepository.findAll(specification, pageable);

        return events.stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    public EventDto createEvent(NewEventDto eventDto, Long initiatorId) {
        log.info("creating new event");
        User user = userRepository.findById(initiatorId)
                .orElseThrow(() -> new NotFoundException(String.format("user with id %d not found", initiatorId)));

        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() ->
                new NotFoundException("category not found"));

        Event event = eventMapper.toEntity(eventDto);

        event.setInitiator(user)
                .setCreatedOn(LocalDateTime.now())
                .setEventDate(eventDto.getEventDate())
                .setCategory(category);

        if (event.getRequestModeration()) {
            event.setEventState(EventState.PENDING);
        } else {
            event.setEventState(EventState.PUBLISHED);
            event.setPublishedOn(LocalDateTime.now());
        }

        return eventMapper.toDto(eventRepository.save(event));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EventDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest updateRequest) {
        checkEventDate(updateRequest.getEventDate());

        Event event = findEventById(eventId);

        if (updateRequest.getStateAction() == StateAction.PUBLISH_EVENT
        && event.getEventState() != EventState.PENDING) {
            throw new DataValidationException("Only pending event can be published");
        }

        if (updateRequest.getStateAction() == StateAction.REJECT_EVENT
        && event.getEventState() == EventState.PUBLISHED) {
            throw new DataValidationException("Published event can not be rejected");
        }

        if (updateRequest.getStateAction() == StateAction.PUBLISH_EVENT) {
            event.setPublishedOn(LocalDateTime.now());
            event.setEventState(EventState.PUBLISHED);
        }

        if (updateRequest.getStateAction() == StateAction.REJECT_EVENT)
            event.setEventState(EventState.CANCELED);

        eventMapper.partialUpdate(updateRequest, event);

        return eventMapper.toDto(event);
    }

    public List<EventShortDto> getEventsByInitiatorId(Long initiatorId, Pageable pageable) {
        return eventRepository.findAllByInitiatorId(initiatorId, pageable)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    private void checkEventDate(LocalDateTime eventDateTime) {
        final int timeDelta = 2;
        if (eventDateTime.isBefore(LocalDateTime.now().plusHours(timeDelta))) {
            throw new DataValidationException(String.format("Event date can not be earlier then %s hours from now time",
                    timeDelta));
        }
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d not found", eventId)));
    }
}