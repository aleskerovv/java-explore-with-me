package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.model.Location;

@Value
@Builder
@Jacksonized
public class NewEventDto {
    String annotation;

    Integer category;

    String description;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    String title;
}
