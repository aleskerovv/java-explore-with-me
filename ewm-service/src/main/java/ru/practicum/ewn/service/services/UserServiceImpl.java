package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.dtos.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.dtos.UserDtoCreate;
import ru.practicum.ewn.service.dtos.UserDtoResponse;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.RequestStatus;
import ru.practicum.ewn.service.handlers.DataValidationException;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.model.Event;
import ru.practicum.ewn.service.model.Request;
import ru.practicum.ewn.service.model.User;
import ru.practicum.ewn.service.repos.EventRepository;
import ru.practicum.ewn.service.repos.RequestRepository;
import ru.practicum.ewn.service.repos.UserRepository;
import ru.practicum.ewn.service.utils.RequestMapper;
import ru.practicum.ewn.service.utils.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {
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

    public ParticipantRequestDtoResponse sendParticipantRequest(Long userId, Long eventId) {
        log.info("creating new request");

        Request request = createRequest(userId, eventId);

        log.info("created new participant request: {}", request);

        return requestMapper.toDto(request);
    }

    private Request createRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("event not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user not found"));

        Request request = new Request();


        if (event.getInitiator().equals(user)) throw new DataValidationException("нельзя отправить самому себе");

        if (!event.getEventState().equals(EventState.PUBLISHED)) throw new DataValidationException("нельзя в неопубликованном");

        if (requestRepository.findRequestsCount(eventId).getCount() == event.getParticipantLimit()) {
            throw new DataValidationException("лимит участников исчерпан");
        }

        if (!requestRepository.findRequestByRequesterId(userId).isEmpty()) {
            throw new DataValidationException("нельзя повторно отправить заявку");
        }

        request.setRequester(user)
                .setEvent(event)
                .setCreated(LocalDateTime.now());

        if (Boolean.TRUE.equals(event.getRequestModeration())) {
            request.setStatus(RequestStatus.PENDING);
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        return requestRepository.save(request);
    }
}
