package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EventShortDto {
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    String eventDate;
    Integer id;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Integer views;
}
