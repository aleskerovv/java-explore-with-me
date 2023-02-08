package ru.practicum.ewn.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.events.dto.*;
import ru.practicum.ewn.service.events.service.UserEventService;
import ru.practicum.ewn.service.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserEventService userEventService;
    private final UserService userService;

    @GetMapping("{id}/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByInitiatorId(@PathVariable Long id,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return userEventService.getEventsByUserId(id, from, size);
    }

    @GetMapping("{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getEventByUserAndEventId(@PathVariable Long userId,
                                             @PathVariable Long eventId) {
        return userEventService.getEventById(userId, eventId);
    }

    @PostMapping("{id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(@PathVariable Long id, @Valid @RequestBody NewEventDto eventDto) {
        return userEventService.createEventByUser(id, eventDto);
    }

    @PatchMapping("{userId}/events/{eventId}")
    public EventDto updateEventByUser(@PathVariable Long userId,
                                      @PathVariable Long eventId,
                                      @RequestBody UserEventUpdateDto eventDto) {
        return userEventService.updateUsersEventById(userId, eventId, eventDto);
    }

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantRequestDtoResponse sendParticipantRequest(@PathVariable(name = "userId") Long userId,
                                                                @RequestParam(name = "eventId") Long eventId) {
        return userService.sendParticipantRequest(userId, eventId);
    }

    @GetMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantRequestDtoResponse> getUsersRequests(@PathVariable Long userId) {
        return userService.getUsersRequests(userId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantRequestDtoResponse cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return userService.cancelSelfRequest(userId, requestId);
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantRequestDtoResponse> getEventsRequests(@PathVariable Long userId,
                                                                 @PathVariable Long eventId) {
        return userEventService.getEventsRequests(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult approveRequests(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          @RequestBody EventRequestStatusUpdateRequest request) {
        return userEventService.updateRequestStatus(userId, eventId, request);
    }
}
