package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import org.springframework.cache.CacheManager;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.dtos.NewEventDto;
import ru.practicum.ewn.service.model.Category;
import ru.practicum.ewn.service.model.Event;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategory")
    Event toEntity(NewEventDto eventDto);

    EventDto toDto(Event event);

    EventShortDto toShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event partialUpdate(EventDto eventDto, @MappingTarget Event event);

    @Named("toCategory")
    Category map(Long id);
}