package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.model.Location;

@Value
@Builder
@Jacksonized
public class UserEventUpdateDto {
    String annotation;
    CategoryDto category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    EventState eventState;
    String title;
}
