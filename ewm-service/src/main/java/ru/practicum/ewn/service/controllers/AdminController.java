package ru.practicum.ewn.service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.dtos.*;
import ru.practicum.ewn.service.services.CategoryServiceImpl;
import ru.practicum.ewn.service.services.EventServiceImpl;
import ru.practicum.ewn.service.services.UserServiceImpl;
import ru.practicum.ewn.service.utils.AdminEventFilter;
import ru.practicum.ewn.service.utils.UserEventFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final UserServiceImpl userService;
    private final CategoryServiceImpl categoryService;
    private final EventServiceImpl eventService;
    private static final String USERS = "/users";
    private static final String CATEGORIES = "/categories";
    private static final String EVENTS = "/events";

    @PostMapping(USERS)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoResponse createUser(@RequestBody UserDtoCreate userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping(USERS + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping(USERS)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDtoResponse> findUsers(@Nullable @RequestParam(name = "ids") List<Long> ids,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.findUsers(ids, from, size);
    }

    @PostMapping(CATEGORIES)
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryDtoCreate categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping(CATEGORIES + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@RequestBody CategoryDtoCreate categoryDto,
                                      @PathVariable Long id) {
        return categoryService.updateCategory(categoryDto, id);
    }

    @DeleteMapping(CATEGORIES + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping(EVENTS)
    public List<EventDto> getAllEvents(@Valid @ModelAttribute("userEventFilter") AdminEventFilter adminEventFilter,
                                       @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                       @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return eventService.findEventsByAdminsFilters(adminEventFilter, from, size);
    }

    @PatchMapping(EVENTS + "/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@PathVariable Long eventId,
                                @RequestBody UpdateEventAdminRequest request) {
        return eventService.updateEventByAdmin(eventId, request);
    }
}
