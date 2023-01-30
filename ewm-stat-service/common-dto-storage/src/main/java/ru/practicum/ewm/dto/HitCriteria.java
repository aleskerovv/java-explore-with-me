package ru.practicum.ewm.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class HitCriteria {
    private List<String> uris;
    @NotNull(message = "'start' can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @NotNull(message = "'end' can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private boolean isUnique;
}
