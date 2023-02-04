package ru.practicum.ewn.service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.model.Location;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class NewEventDto {
    String annotation;
    Long category;
    String description;
    Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
}
