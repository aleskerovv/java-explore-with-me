package ru.practicum.ewn.service.compilations.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.events.dto.EventShortDto;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CompilationDtoCreate {
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}