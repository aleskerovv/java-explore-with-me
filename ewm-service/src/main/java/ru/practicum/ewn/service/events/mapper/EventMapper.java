package ru.practicum.ewn.service.events.mapper;

import org.mapstruct.*;
import ru.practicum.ewn.service.events.dto.*;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.category.mapper.CategoryMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = CategoryMapper.class)
public interface EventMapper {
    @Mapping(target = "category.id", source = "category")
    Event toEntity(NewEventDto eventDto);

    EventDto toDto(Event event);

    EventShortDto toShortDto(Event event);

    EventShortDto toShortDto(EventDto eventDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(UserEventUpdateDto eventDto, @MappingTarget Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(UpdateEventAdminRequest eventDto, @MappingTarget Event event);

    EventDto toDtoWithViews(Event event, Long views);

    EventShortDto toShortDtoWithViews(Event event, Long views);
}