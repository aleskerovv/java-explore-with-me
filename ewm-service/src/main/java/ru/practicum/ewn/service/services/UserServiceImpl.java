package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.dtos.UserDtoCreate;
import ru.practicum.ewn.service.dtos.UserDtoResponse;
import ru.practicum.ewn.service.model.User;
import ru.practicum.ewn.service.utils.UserMapper;
import ru.practicum.ewn.service.repos.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
}
