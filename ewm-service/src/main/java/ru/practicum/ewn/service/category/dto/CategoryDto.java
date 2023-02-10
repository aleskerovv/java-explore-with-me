package ru.practicum.ewn.service.category.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Jacksonized
public class CategoryDto {
    private Integer id;
    private String name;
}
