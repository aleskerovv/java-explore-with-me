package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.dtos.CategoryDto;
import ru.practicum.ewn.service.dtos.CategoryDtoCreate;
import ru.practicum.ewn.service.model.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDtoCreate categoryDto);

    CategoryDto toDto(Category category);
}