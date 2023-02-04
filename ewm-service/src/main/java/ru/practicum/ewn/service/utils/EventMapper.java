package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.dtos.NewEventDto;
import ru.practicum.ewn.service.dtos.UpdateEventAdminRequest;
import ru.practicum.ewn.service.model.Category;
import ru.practicum.ewn.service.model.Event;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CategoryMapper.class)
public interface EventMapper {
    @Mapping(target = "category.id", source = "category")
    Event toEntity(NewEventDto eventDto);

    EventDto toDto(Event event);

    EventShortDto toShortDto(Event event);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "category.id", source = "category")
//    void partialUpdate(EventDto eventDto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(UpdateEventAdminRequest eventDto, @MappingTarget Event event);

    @Named("toCategory")
    Category map(Long id);
}