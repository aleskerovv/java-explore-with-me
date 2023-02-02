package ru.practicum.ewn.service.utils;

import org.mapstruct.*;
import ru.practicum.ewn.service.dtos.LocationDto;
import ru.practicum.ewn.service.model.Location;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LocationMapper {
    Location toEntity(LocationDto locationDto);

    LocationDto toDto(Location location);
}