package ru.practicum.ewn.service.compilations.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class CompilationUpdateDto {
    List<Integer> events;
    Boolean pinned;
    String title;
}
