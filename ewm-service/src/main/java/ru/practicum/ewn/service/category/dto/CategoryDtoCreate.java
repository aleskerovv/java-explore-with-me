package ru.practicum.ewn.service.category.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class CategoryDtoCreate {
    @NotNull
    String name;
}
