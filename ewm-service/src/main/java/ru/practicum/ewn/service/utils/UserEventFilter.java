package ru.practicum.ewn.service.utils;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewn.service.enums.EventSortParam;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEventFilter {
    private String text;
    private List<Long> categories;
    private Boolean paid = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable = false;
    private EventSortParam sort = EventSortParam.EVENT_DATE;
}
