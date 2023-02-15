package ru.practicum.ewn.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewn.service.events.dto.*;
import ru.practicum.ewn.service.events.service.EventService;
import ru.practicum.ewn.service.events.service.UserEventService;
import ru.practicum.ewn.service.utils.UserEventFilter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    private final EventService eventService;
    private final UserEventService userEventService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto getEventById(@PathVariable Long id, HttpServletRequest servletRequest,
                                 @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .timestamp(LocalDateTime.now())
                .build();

        return eventService.getEventById(id, endpointHitDto);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEvents(@Valid @ModelAttribute("userEventFilter") UserEventFilter userEventFilter,
                                            @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                            @RequestParam(name = "size", defaultValue = "10", required = false) Integer size,
                                            HttpServletRequest servletRequest,
                                            @Value("${main.service.name}") String appName) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .ip(servletRequest.getRemoteAddr())
                .uri(servletRequest.getRequestURI())
                .app(appName)
                .timestamp(LocalDateTime.now())
                .build();

        return eventService.publicGetEvents(userEventFilter, from, size, endpointHitDto);
    }

    @GetMapping("{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> getCommentsByEventId(@PathVariable Long eventId,
                                                         @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                                         @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return eventService.getCommentsByEventId(eventId, from, size);
    }

    @PostMapping("comments/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse addComment(@PathVariable Long userId,
                                         @Valid @RequestBody CommentDtoCreate commentDtoCreate) {
        return userEventService.addComment(commentDtoCreate, userId);
    }

    @PatchMapping("comments/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse updateComment(@PathVariable Long userId,
                                            @PathVariable Long commentId,
                                            @Valid @RequestBody CommentDtoUpdate commentDtoUpdate) {
        return userEventService.updateComment(commentDtoUpdate, commentId, userId);
    }

    @GetMapping("comments/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> getCommentByAuthorId(@PathVariable Long userId,
                                                         @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                                         @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return userEventService.getCommentsByAuthorId(userId, from, size);
    }

    @DeleteMapping("comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId) {
        userEventService.deleteComment(commentId);
    }
}
