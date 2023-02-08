package ru.practicum.ewn.service.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.category.dto.CategoryDto;
import ru.practicum.ewn.service.category.dto.CategoryDtoCreate;
import ru.practicum.ewn.service.category.service.CategoryService;
import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.dto.CompilationDtoCreate;
import ru.practicum.ewn.service.compilations.dto.CompilationUpdateDto;
import ru.practicum.ewn.service.compilations.service.CompilationService;
import ru.practicum.ewn.service.events.dto.EventDto;
import ru.practicum.ewn.service.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewn.service.events.service.AdminEventService;
import ru.practicum.ewn.service.users.dto.UserDtoCreate;
import ru.practicum.ewn.service.users.dto.UserDtoResponse;
import ru.practicum.ewn.service.users.service.UserService;
import ru.practicum.ewn.service.utils.AdminEventFilter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

//TODO: separate admin controller to another controllers

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final UserService userService;
    private final CategoryService categoryService;
    private final AdminEventService adminEventService;
    private final CompilationService compilationService;
    private static final String USERS = "/users";
    private static final String CATEGORIES = "/categories";
    private static final String EVENTS = "/events";
    private static final String COMPILATIONS = "/compilations";

    @PostMapping(USERS)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtoResponse createUser(@Valid @RequestBody UserDtoCreate userDto) {
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
    public CategoryDto createCategory(@Valid @RequestBody CategoryDtoCreate categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PatchMapping(CATEGORIES + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDtoCreate categoryDto,
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
        return adminEventService.adminGetEvents(adminEventFilter, from, size);
    }

    @PatchMapping(EVENTS + "/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEvent(@PathVariable Long eventId,
                                @RequestBody UpdateEventAdminRequest request) {
        return adminEventService.updateEventByAdmin(eventId, request);
    }

    @PostMapping(COMPILATIONS)
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody CompilationDtoCreate compilationDto) {
        return compilationService.createCompilation(compilationDto);
    }

    @PatchMapping(COMPILATIONS + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@RequestBody CompilationUpdateDto compilationDto, @PathVariable Long id) {
        return compilationService.updateCompilation(id, compilationDto);
    }

    @DeleteMapping(COMPILATIONS + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        compilationService.deleteCompilation(id);
    }
}
