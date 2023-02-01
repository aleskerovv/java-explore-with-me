package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.dtos.EventDto;
import ru.practicum.ewn.service.dtos.EventShortDto;
import ru.practicum.ewn.service.model.Event;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventDto eventDto);

    EventDto toDto(Event event);

    EventShortDto toShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event partialUpdate(EventDto eventDto, @MappingTarget Event event);
}