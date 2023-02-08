package ru.practicum.ewn.service.compilations.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
@Builder
@Jacksonized
public class CompilationDtoCreate {
    @NotNull
    List<Long> events;
    @NotNull
    Boolean pinned;
    @NotNull
    String title;
}