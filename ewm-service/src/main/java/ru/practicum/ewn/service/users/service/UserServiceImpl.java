package ru.practicum.ewn.service.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.RequestStatus;
import ru.practicum.ewn.service.events.dao.EventRepository;
import ru.practicum.ewn.service.events.dao.RequestRepository;
import ru.practicum.ewn.service.events.dto.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.events.model.Request;
import ru.practicum.ewn.service.events.model.RequestMapper;
import ru.practicum.ewn.service.handlers.DataValidationException;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.users.dao.UserRepository;
import ru.practicum.ewn.service.users.dto.UserDtoCreate;
import ru.practicum.ewn.service.users.dto.UserDtoResponse;
import ru.practicum.ewn.service.users.mapper.UserMapper;
import ru.practicum.ewn.service.users.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    public UserDtoResponse createUser(UserDtoCreate userDto) {
        log.info("creating new user {}", userDto);

        User user = userRepository.save(userMapper.toEntity(userDto));

        log.info("created new user entity {}", userDto);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        checkUserExists(id);
        userRepository.deleteById(id);
    }

    public List<UserDtoResponse> findUsers(List<Long> ids, Integer from, Integer size) {
        Pageable pg = PageRequest.of(from, size);

        return ids != null ? userRepository.findByParams(ids, pg).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList())

                : userRepository.findByParams(pg).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ParticipantRequestDtoResponse> getUsersRequests(Long userId) {
        checkUserExists(userId);

        return requestRepository.findRequestByRequesterId(userId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ParticipantRequestDtoResponse cancelSelfRequest(Long userId, Long requestId) {
        checkUserExists(userId);

        Request request = requestRepository.findRequestByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new NotFoundException(String.format("request with id=%d not found", requestId)));

        request.setStatus(RequestStatus.CANCELED);

        Event event = eventRepository.findById(request.getEvent().getId()).orElseThrow(() ->
                new NotFoundException("not found"));

        event.setConfirmedRequests(event.getConfirmedRequests() - 1);

        return requestMapper.toDto(request);
    }

    public ParticipantRequestDtoResponse sendParticipantRequest(Long userId, Long eventId) {
        log.info("creating new request");

        Request request = createRequest(userId, eventId);

        log.info("created new participant request: {}", request);

        return requestMapper.toDto(request);
    }

    private Request createRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("event not found"));

        User user = this.checkUserExists(userId);

        Request request = new Request();


        if (event.getInitiator().equals(user))
            throw new DataValidationException("You can not send request for own event");

        if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit()))
            throw new DataValidationException("Participant limit for event exceeded");

        if (!event.getEventState().equals(EventState.PUBLISHED))
            throw new DataValidationException("Can not send request for not published event");

        if (!requestRepository.findRequestByRequesterIdAndEventId(userId, eventId).isEmpty()) {
            throw new DataValidationException("You already sent participation request for this event");
        }

        request.setRequester(user)
                .setEvent(event)
                .setCreated(LocalDateTime.now());

        if (Boolean.TRUE.equals(event.getRequestModeration())) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }

        return requestRepository.save(request);
    }

    private User checkUserExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("User with id=%d not found", userId)));
    }
}
