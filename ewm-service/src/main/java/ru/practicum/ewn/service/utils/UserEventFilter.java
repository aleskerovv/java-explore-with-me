package ru.practicum.ewn.service.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewn.service.enums.EventSortParam;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class UserEventFilter {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;
    @Builder.Default
    private Boolean onlyAvailable = false;
    @Builder.Default
    private EventSortParam sort = EventSortParam.EVENT_DATE;
}
