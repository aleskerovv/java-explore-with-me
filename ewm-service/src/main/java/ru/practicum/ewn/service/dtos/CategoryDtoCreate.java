package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CategoryDtoCreate {
    String name;
}
