package ru.practicum.ewn.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.dtos.NewEventDto;
import ru.practicum.ewn.service.dtos.ParticipantRequestDtoResponse;
import ru.practicum.ewn.service.services.EventServiceImpl;
import ru.practicum.ewn.service.services.UserServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final EventServiceImpl eventService;
    private final UserServiceImpl userService;

    @PostMapping("{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable Long id, @RequestBody NewEventDto eventDto) {
        return eventService.createEvent(eventDto, id);
    }

    @GetMapping("{id}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByInitiatorId(@PathVariable Long id,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return eventService.getEventsByInitiatorId(id, pageable);
    }

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantRequestDtoResponse sendParticipantRequest(@PathVariable(name = "userId") Long userId,
                                                                @RequestParam(name = "eventId") Long eventId) {
        return userService.sendParticipantRequest(userId, eventId);
    }
}
