package ru.practicum.ewn.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.dtos.UserDtoCreate;
import ru.practicum.ewn.service.dtos.UserDtoResponse;
import ru.practicum.ewn.service.services.UserServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final UserServiceImpl userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoResponse createUser(@RequestBody UserDtoCreate userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoResponse> findUsers(@Nullable @RequestParam(name = "ids") List<Long> ids,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.findUsers(ids, from, size);
    }
}
