package ru.practicum.ewn.service.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewn.service.category.dto.CategoryDto;
import ru.practicum.ewn.service.category.dto.CategoryDtoCreate;
import ru.practicum.ewn.service.category.model.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDtoCreate categoryDto);

    CategoryDto toDto(Category category);

    @Named("toCategory")
    Category map(Long id);
}