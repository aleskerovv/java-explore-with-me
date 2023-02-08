package ru.practicum.ewn.service.users.service;

import ru.practicum.ewn.service.events.dto.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.users.dto.UserDtoCreate;
import ru.practicum.ewn.service.users.dto.UserDtoResponse;

import java.util.List;

public interface UserService {

    UserDtoResponse createUser(UserDtoCreate userDto);

    void deleteUser(Long id);

    List<UserDtoResponse> findUsers(List<Long> ids, Integer from, Integer size);

    List<ParticipantRequestDtoResponse> getUsersRequests(Long userId);

    ParticipantRequestDtoResponse cancelSelfRequest(Long userId, Long requestId);

    ParticipantRequestDtoResponse sendParticipantRequest(Long userId, Long eventId);
}
