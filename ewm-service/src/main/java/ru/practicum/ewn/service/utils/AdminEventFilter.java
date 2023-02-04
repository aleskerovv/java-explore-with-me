package ru.practicum.ewn.service.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewn.service.enums.EventState;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class AdminEventFilter {
    List<Long> users;
    List<EventState> states;
    List<Long> categories;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime rangeEnd;
}
