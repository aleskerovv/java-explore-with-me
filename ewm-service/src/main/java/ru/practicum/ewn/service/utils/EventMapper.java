package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.events.dto.*;
import ru.practicum.ewn.service.category.model.Category;
import ru.practicum.ewn.service.events.model.Event;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CategoryMapper.class)
public interface EventMapper {
    @Mapping(target = "category.id", source = "category")
    Event toEntity(NewEventDto eventDto);

    EventDto toDto(Event event);

    EventShortDto toShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(UserEventUpdateDto eventDto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(UpdateEventAdminRequest eventDto, @MappingTarget Event event);

    @Named("toCategory")
    Category map(Long id);
}