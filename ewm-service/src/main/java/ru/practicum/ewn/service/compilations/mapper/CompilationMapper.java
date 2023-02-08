package ru.practicum.ewn.service.compilations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.dto.CompilationDtoCreate;
import ru.practicum.ewn.service.compilations.model.Compilation;
import ru.practicum.ewn.service.events.model.Event;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", source = "events")
    Compilation toEntity(CompilationDtoCreate compilationDto, List<Event> events);

    @Mapping(target = "events", source = "events")
    CompilationDto toDto(Compilation compilation);
}