package ru.practicum.ewn.service.compilations.mapper;

import org.mapstruct.*;
import ru.practicum.ewn.service.compilations.CompilationController;
import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.dto.CompilationDtoCreate;
import ru.practicum.ewn.service.compilations.dto.CompilationUpdateDto;
import ru.practicum.ewn.service.compilations.model.Compilation;
import ru.practicum.ewn.service.events.model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", source = "events")
    Compilation toEntity(CompilationDtoCreate compilationDto, List<Event> events);

    @Mapping(target = "events", source = "events")
    CompilationDto toDto(Compilation compilation);
}