package ru.practicum.ewn.service.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.events.model.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class NewEventDto {
    @NotNull
    String annotation;
    @NotNull
    Long category;
    @NotNull
    String description;
    @NotNull
    Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    LocalDateTime eventDate;
    @NotNull
    Boolean paid;
    @NotNull
    @PositiveOrZero
    Integer participantLimit;
    @NotNull
    Boolean requestModeration;
    @NotNull
    String title;
}
