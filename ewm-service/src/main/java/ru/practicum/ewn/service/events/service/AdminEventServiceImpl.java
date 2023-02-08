package ru.practicum.ewn.service.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.StateAction;
import ru.practicum.ewn.service.events.dao.EventRepository;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewn.service.events.mapper.EventMapper;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.handlers.DataValidationException;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.utils.AdminEventFilter;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewn.service.utils.AdminEventSpecification.getAdminsFilters;
import static ru.practicum.ewn.service.utils.DataChecker.checkActionState;
import static ru.practicum.ewn.service.utils.DataChecker.dateTimeChecker;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> adminGetEvents(AdminEventFilter filter, int from, int size) {
        log.info("getting all events by admin");
        Pageable pageable = PageRequest.of(from, size);

        Specification<Event> specification = getAdminsFilters(filter);

        return eventRepository.findAll(specification, pageable).stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EventDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest updateRequest) {
        dateTimeChecker(updateRequest.getEventDate());

        Event event = findEventById(eventId);

        if (updateRequest.getStateAction() == StateAction.PUBLISH_EVENT
                && event.getEventState() != EventState.PENDING) {
            throw new DataValidationException("Only pending event can be published");
        }

        if (updateRequest.getStateAction() == StateAction.REJECT_EVENT
                && event.getEventState() == EventState.PUBLISHED) {
            throw new DataValidationException("Published event can not be rejected");
        }

        checkActionState(updateRequest.getStateAction(), event);

        eventMapper.partialUpdate(updateRequest, event);

        return eventMapper.toDto(event);
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d not found", eventId)));
    }
}
