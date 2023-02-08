package ru.practicum.ewn.service.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.category.dao.CategoryRepository;
import ru.practicum.ewn.service.category.model.Category;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.RequestStatus;
import ru.practicum.ewn.service.events.dao.EventRepository;
import ru.practicum.ewn.service.events.dao.RequestRepository;
import ru.practicum.ewn.service.events.dto.*;
import ru.practicum.ewn.service.events.mapper.EventMapper;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.events.model.Request;
import ru.practicum.ewn.service.events.model.RequestMapper;
import ru.practicum.ewn.service.handlers.DataValidationException;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.users.dao.UserRepository;
import ru.practicum.ewn.service.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.ewn.service.utils.DataChecker.checkActionState;
import static ru.practicum.ewn.service.utils.DataChecker.dateTimeChecker;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {
    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByUserId(Long initiatorId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return eventRepository.findAllByInitiatorId(initiatorId, pageable)
                .stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EventDto createEventByUser(Long initiatorId, NewEventDto eventDto) {

        dateTimeChecker(eventDto.getEventDate());

        log.info("creating new event");
        User user = userRepository.findById(initiatorId)
                .orElseThrow(() -> new NotFoundException(String.format("user with id %d not found", initiatorId)));

        Category category = categoryRepository.findById(eventDto.getCategory()).orElseThrow(() ->
                new NotFoundException("category not found"));

        Event event = eventMapper.toEntity(eventDto);

        event.setInitiator(user)
                .setCreatedOn(LocalDateTime.now())
                .setEventDate(eventDto.getEventDate())
                .setCategory(category)
                .setConfirmedRequests(0)
                .setEventState(EventState.PENDING);

        return eventMapper.toDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto getEventById(Long userId, Long eventId) {
        User initiator = getUserIfExists(userId);

        Event event = Optional.of(eventRepository.findEventByIdAndInitiator(eventId, initiator))
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d for user with id %d not found",
                        eventId, userId)));

        int requests = requestRepository.getConfirmedRequestsCount(event.getId());

        EventDto dto = eventMapper.toDto(event);
        dto.setConfirmedRequests(requests);

        return dto;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EventDto updateUsersEventById(Long userId, Long eventId, UserEventUpdateDto eventDto) {
        log.info("updating event with id {}", eventId);

        dateTimeChecker(eventDto.getEventDate());

        Event event = Optional.of(eventRepository.findEventByIdAndInitiator(eventId, getUserIfExists(userId)))
                .orElseThrow(() -> new NotFoundException(String.format("event with id %d for user with id %d not found",
                        eventId, userId)));

        if (event.getEventState() == EventState.PUBLISHED) {
            throw new DataValidationException("Only pending or canceled event can be edited");
        }

        checkActionState(eventDto.getStateAction(), event);

        eventMapper.partialUpdate(eventDto, event);

        return eventMapper.toDto(event);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipantRequestDtoResponse> getEventsRequests(Long userId, Long eventId) {
        getEventIfExists(eventId);

        getUserIfExists(userId);

        List<Request> usersRequests = requestRepository.findRequestsByInitiatorIdIdAndEventId(userId, eventId);

        return usersRequests.stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId,
                                                              Long eventId,
                                                              EventRequestStatusUpdateRequest updateRequest) {

        Event event = getEventIfExists(eventId);

        if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
            throw new DataValidationException("Participant limit for event exceeded");
        }

        List<ParticipantRequestDtoResponse> confirmed = new ArrayList<>();
        List<ParticipantRequestDtoResponse> rejected = new ArrayList<>();

        requestRepository.findRequestsByInitiatorIdIdAndEventId(userId, eventId, updateRequest.getRequestIds())
                .stream()
                .peek(req -> {
                    if (req.getStatus().equals(RequestStatus.PENDING)) {
                        if (updateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
                            if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                                req.setStatus(RequestStatus.CONFIRMED);
                                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                            } else {
                                req.setStatus(RequestStatus.REJECTED);
                            }
                        } else {
                            req.setStatus(RequestStatus.REJECTED);
                        }
                    } else {
                        throw new DataValidationException("You can confirm only pending requests");
                    }
                })
                .map(requestMapper::toDto)
                .forEach(req -> {
                    if (req.getRequestStatus().equals(RequestStatus.CONFIRMED))
                        confirmed.add(req);
                    else
                        rejected.add(req);
                });

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }

    private User getUserIfExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%d not found", userId)));
    }

    private Event getEventIfExists(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Event with id=%d not found", eventId)));
    }
}
