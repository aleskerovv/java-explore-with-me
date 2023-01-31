package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.dtos.EventShortDto;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CompilationDtoCreate {
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}